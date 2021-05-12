import quantopian.algorithm as algo
from quantopian.pipeline import Pipeline
from quantopian.pipeline.data.builtin import USEquityPricing
from quantopian.pipeline.data import Fundamentals
from quantopian.pipeline.filters import Q500US
from quantopian.pipeline.filters import Q1500US
from quantopian.pipeline.filters import QTradableStocksUS

from quantopian.pipeline.factors import SimpleBeta
from quantopian.pipeline.factors import AnnualizedVolatility
from quantopian.pipeline.factors import Returns
from quantopian.pipeline.factors import SimpleMovingAverage
from quantopian.pipeline.factors import DailyReturns

from quantopian.pipeline.experimental import risk_loading_pipeline

import talib
import numpy as np
import pandas as pd

def initialize(context):
    
    set_slippage(slippage.FixedSlippage(spread = 0.05)) 
    algo.attach_pipeline(make_pipeline(), 'pipeline')    
    algo.attach_pipeline(risk_loading_pipeline(), 'risk_factors')
    
    #Schedule Functions
    schedule_function(trade, date_rules.week_start(2) , time_rules.market_close(minutes=30))
    schedule_function(trade_bonds, date_rules.month_end(), time_rules.market_close(minutes=20))
    
    #This is for the trend following filter
    context.spy = sid(8554)
    context.TF_filter = False
    context.TF_lookback = 126
    
    #Set number of securities to buy and bonds fund (when we are out of stocks)
    context.Target_securities_to_buy = 20.0
    context.bonds = sid(23870)
    
    
 
def make_pipeline():

    # Base universe set to the Q500US
    universe = Q500US()

    roe = Fundamentals.roe.latest
    
    new_returns = Returns(
        window_length=5, 
        mask = universe
    )
    
    new_returns = new_returns.zscore()
    returns_range = new_returns.percentile_between(1, 30)
    
    new_volatility = AnnualizedVolatility(
        mask = universe
    )
    
    new_volatility = new_volatility.zscore()
    volatility_range = new_volatility.percentile_between(1, 30)
    
    
    pipe = Pipeline(
        columns={
            'roe': roe,
            'returns': returns_range,
            'volatility': volatility_range
        },
        screen=universe
    )
    return pipe

def before_trading_start(context, data):
    
    context.output = algo.pipeline_output('pipeline')
    context.returns_range = context.output['returns']
    context.volatility_range = context.output['volatility']
    
    context.security_list = context.output.index
    
        
def trade(context, data):
    
    returns_range = context.returns_range
    volatility_range = context.volatility_range
    
    ############Trend Filter############
    TF_hist = data.history(context.spy , "close", 140, "1d")
    TF_check = TF_hist.pct_change(context.TF_lookback).iloc[-1]

    if TF_check > 0.0:
        context.TF_filter = True
    else:
        context.TF_filter = False
    ############Trend Filter End############
    
    #prices of top 500 stocks
    prices = data.history(context.security_list,"close", 180, "1d")      
    
    df = context.output  
    
    #takes top 100 stocks with best ROE
    top_n_roe = df['roe'].nlargest(100)
    #Calculate the momentum of the top ROE stocks   
    quality_momentum = prices[top_n_roe.index][:-12].pct_change(100).iloc[-1]
    #Grab stocks with best momentum    
    top_n_by_momentum = quality_momentum.nlargest(25)
           
    for x in context.portfolio.positions:
        if (x.sid == context.bonds):
            pass
        elif x not in top_n_by_momentum:
            if context.TF_filter == False:
                order_target_percent(x, -(1.0 / context.Target_securities_to_buy))
            else:
                order_target_percent(x, 0)
    
    for x in top_n_by_momentum.index:
        if x not in context.portfolio.positions and context.TF_filter==True and x in returns_range and x in volatility_range:
            order_target_percent(x, (1.0 / context.Target_securities_to_buy)*1.25)
    
    for x in context.security_list:
        rsi = talib.RSI(prices[x], timeperiod=14)[-1]
        if x not in top_n_by_momentum.index and rsi < 10:
            order_target_percent(x, (1.0 / context.Target_securities_to_buy) * 1.4)
        elif x not in top_n_by_momentum.index and rsi > 90:
            order_target_percent(x, -(1.0 / context.Target_securities_to_buy) * 1.4)
            
            

            
            
def trade_bonds(context , data):
    amount_of_current_positions=0
    if context.portfolio.positions[context.bonds].amount == 0:
        amount_of_current_positions = len(context.portfolio.positions)
    if context.portfolio.positions[context.bonds].amount > 0:
        amount_of_current_positions = len(context.portfolio.positions) - 1
    percent_bonds_to_buy = .15
    order_target_percent(context.bonds , percent_bonds_to_buy)