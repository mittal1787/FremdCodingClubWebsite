import requests
from bs4 import BeautifulSoup
import tkinter as tk
import numpy as np
from sklearn.naive_bayes import GaussianNB
import matplotlib.pyplot as plt


Continue = ""

Top_Stock_List = []
Top_Stock_List_Percent = []
Top_Stock_List_Value = []

Stocks_Owned_List = []
Amount_Stocks_Owned_List = []
Money = 1000
Stock_Price = 0
Next = ""
Stock_Value = 0
Stocks_Purchased = 0
Price_Check = 0
Bal = 0
High = ""
enterallowed=False
def find_stock_value(Name):
    result = requests.get("https://www.marketwatch.com/investing/stock/" + str(Name))

    soup = BeautifulSoup(result.content, "html.parser")

    for link in soup.find_all("bg-quote", class_="value"):
        Stock_Price = link.text
    for link in soup.find_all("span", class_="value"):
        Stock_Price = link.text
    return Stock_Price

def enter():
    global enterallowed
    if enterallowed==True:
        enterallowed=False
        global option
        global Money
        global name
        if option=='search':
            searchenter()
        elif option=='buy':
            buyenter()
        elif option=='buy2':
            Money = buyenter2(name, Money)
            mon['text']='$'+round(Money, 2)
        elif option=='sell':
            sellenter()
        elif option=='sell2':
            Money = sellenter2(name, Money)
            mon['text']='$'+round(Money, 2)
        elif option=='find':
            findenter(entry.get())
        elif option=='mlearning':
            text['text']=mlearnenter(entry.get())

def enter2(a):
    global enterallowed
    if enterallowed==True:
        enterallowed=False
        global option
        global Money
        global name
        if option=='search':
            searchenter()
        elif option=='buy':
            buyenter()
        elif option=='buy2':
            Money = buyenter2(name, Money)
            mon['text']=round(Money, 2)
        elif option=='sell':
            sellenter()
        elif option=='sell2':
            Money = sellenter2(name, Money)
            mon['text']=round(Money, 2)
        elif option=='find':
            findenter(entry.get())
        elif option=='mlearning':
            text['text']=mlearnenter(entry.get())
            
def search2():
    global option
    option='search'
    global w
    text['text']='Type the abbreviation of the stock name: '
    text.pack()
    entry.pack(side=tk.TOP,padx=2,pady=22)
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack(side=tk.LEFT,padx=2)

def searchenter():
    global varenter
    varenter=entry.get()
    varenter=varenter.upper()
    text['text']="The value of the stock is $" + find_stock_value(varenter)
    text.pack()
    entry.pack_forget()
    

def buy2():
    global enterallowed
    enterallowed=True
    global option
    option='buy'
    global w
    text['text']='Type the abbreviation of the stock name to buy: '
    text.pack()
    entry.pack(side=tk.TOP,padx=2,pady=22)
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack(side=tk.LEFT,padx=2)

def buyenter():
    if entry.get() != '':
        global enterallowed
        enterallowed=True
        global varenter
        global name
        name=entry.get()
        varenter=entry.get()
        entry.delete(0, 'end')
        entry.insert(0, "")
        text['text']="Type in how many of this stock you want to buy:"
        text.pack()
        global option
        option='buy2'

def buyenter2(Name, InvValue):
    if entry.get() != '':
        Name=Name.upper()
        global varenter
        varenter=entry.get()
        NewValue = ""
        Stocks_Purchased = float(varenter)
        Value = str(find_stock_value(Name))
        entry.delete(0, 'end')
        entry.insert(0, "")
        for Letter in Value:
            if Letter.isdigit() == True:
                NewValue = str(NewValue) + str(Letter)
            elif str(Letter) == str("."):
                NewValue = str(NewValue) + str(Letter)        
        Value = float(NewValue)
        Stock_Value = Value*Stocks_Purchased
        entry.pack_forget()
        text['text']="You bought " + str(Stocks_Purchased) + " " + str(Name) + "(s) for $" + str(round(Stock_Value,2))+'. '
        InvValue -= Stock_Value
        if float(InvValue) < float(0):
            InvValue += Stock_Value
            text['text']="You currently don't have enough funds to make this purchase"
        else:
            InvValue = round(InvValue, 2)
            #text['text']+="You currently have $" + str(InvValue) + " left"
            b7.pack_forget()
            if Name in Stocks_Owned_List:
                find = Stocks_Owned_List.index(Name)
                
                if find != "":
                    Amount_Stocks_Owned_List[find] = int(Amount_Stocks_Owned_List[find])+int(Stocks_Purchased)
            else:
                Stocks_Owned_List.append(Name)
                Amount_Stocks_Owned_List.append(Stocks_Purchased)

        return InvValue

def sell2():
    global enterallowed
    enterallowed=True
    global option
    option='sell'
    global w
    text['text']='Type the abbreviation of the stock name to sell: '
    text.pack()
    entry.pack(side=tk.TOP,padx=2,pady=22)
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack(side=tk.LEFT,padx=2)

def sellenter():
    if entry.get() != '':
        global enterallowed
        enterallowed=True
        global varenter
        global name
        name=entry.get()
        varenter=entry.get()
        entry.delete(0, 'end')
        entry.insert(0, "")
        text['text']="Type in how many of this stock you want to sell:"
        text.pack()
        global option
        option='sell2'

def sellenter2(Name, AccMoney):
    if entry.get() != '':
        Name=Name.upper()
        global varenter
        varenter=entry.get()
        entry.delete(0, 'end')
        entry.insert(0, "")
        entry.pack_forget()
        b7.pack_forget()
        NewValue = ""
        Stocks_Purchased = float(varenter)
        if str(Name) in Stocks_Owned_List:
            ListPos = Stocks_Owned_List.index(Name)
            ListNum = int(Amount_Stocks_Owned_List[ListPos])
            Value = find_stock_value(Name)
            for Letter in Value:
                if Letter.isdigit() == True:
                    NewValue = str(NewValue) + str(Letter)
                elif str(Letter) == str("."):
                    NewValue = str(NewValue) + str(Letter)
                    
            Value = float(NewValue)
            Stock_Value = Value*Stocks_Purchased
            
            if int(ListNum) < int(Stocks_Purchased):
                text['text']="You don't currently own that many of this stock"
            elif int(ListNum) >= int(Stocks_Purchased):
                text['text']="You sold "+str(Stocks_Purchased)+" "+str(Name)+"(s) for $"+str(Stock_Value)+"."
                Amount_Stocks_Owned_List[ListPos] -= Stocks_Purchased
                AccMoney += Stock_Value
                AccMoney = round(AccMoney, 2)
                #print(AccMoney)
                if Amount_Stocks_Owned_List[ListPos]==0:
                    Stocks_Owned_List.remove(Stocks_Owned_List[ListPos])
                    Amount_Stocks_Owned_List.remove(Amount_Stocks_Owned_List[ListPos])
                    
        else:
            text['text']="You don't currently own this stock"
        return AccMoney

def back():
    entry.delete(0, 'end')
    entry.insert(0, "")
    global option
    option=''
    frame.pack(side=tk.BOTTOM)
    entry.pack_forget()
    frame2.pack_forget()
    supertext="Welcome to The Stock Market Simulator! Press a button below to manage stocks."
    text['text']=supertext
    text.pack()
    b10.pack_forget()
def inv2():
    text['text']='You have '
    entry.pack_forget()
    frame.pack_forget()
    b7.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    if len(Stocks_Owned_List)==0:
        text['text']='You have no stocks.'
    elif len(Stocks_Owned_List)==1:
        text['text']+=str(Amount_Stocks_Owned_List[0])+' '
        text['text']+=str(Stocks_Owned_List[0])+'(s). '
    elif len(Stocks_Owned_List)==2:
        text['text']+=str(Amount_Stocks_Owned_List[0])+' '
        text['text']+=str(Stocks_Owned_List[0])+'(s) and '
        text['text']+=str(Amount_Stocks_Owned_List[1])+' '
        text['text']+=str(Stocks_Owned_List[1])+'(s).'
    else:
        for x in range(len(Stocks_Owned_List)):
            if x != len(Stocks_Owned_List)-1:
                text['text']+=str(Amount_Stocks_Owned_List[x])+' '
                text['text']+=str(Stocks_Owned_List[x])+'(s), '
            else:
                text['text']+='and '+str(Amount_Stocks_Owned_List[x])+' '
                text['text']+=str(Stocks_Owned_List[x])+'(s). '

def high2():
    result = requests.get("http://www.wsj.com/mdc/public/page/2_3021-gainnnm-gainer.html")
    soup = BeautifulSoup(result.content, "html.parser")
    linkv = soup.find_all("td", class_="pnum")
    linkn = soup.find_all("a", class_="linkb")
    vcounter = 0
    vcounter1 = 0
    vcounter12 = 0
    ncounter = 0
    for link in linkv:
        vcounter += 1
        if vcounter % 3 == 0:
            vcounter1 += 1
            if vcounter1 <= 5:
                Top_Stock_List_Percent.append(link.text)
        if vcounter % 3 == 1:
            vcounter12 += 1
            if vcounter12 <= 5:
                Top_Stock_List_Value.append(link.text)
    for link in linkn:
        ncounter += 1
        if ncounter <= 5:
            Top_Stock_List.append(link.text)
    text['text']='Top rising stocks are: \n'
    for x in range(0, 5):
        if x != 4:
            text['text']+=str(Top_Stock_List[x].strip())+',with a '
            text['text']+=str(Top_Stock_List_Percent[x])+'% rise and costs $'+Top_Stock_List_Value[x].lstrip('$')+'; \n '
        else:
            text['text']+='and '+str(Top_Stock_List[x].strip())+', with a '
            text['text']+=str(Top_Stock_List_Percent[x])+'% rise and costs $'+Top_Stock_List_Value[x].lstrip('$')+'.'
    #    print(Top_Stock_List[x])
    #    print("Percent increase: " + str(Top_Stock_List_Percent[x]) + "%")
    #    print(Top_Stock_List_Value[x])
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack_forget()
def find2():
    global enterallowed
    enterallowed=True
    global option
    option='find'
    global w
    text['text']='Type the abbreviation of the stock name: '
    text.pack()
    entry.pack(side=tk.TOP,padx=2,pady=22)
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack(side=tk.LEFT,padx=2)
    
def findenter(Name):
    if entry.get() != '':
        b10.pack(side=tk.BOTTOM,padx=2)
        global varenter
        Name=Name.upper()
        varenter=entry.get()
        varenter=varenter.upper()
        entry.delete(0, 'end')
        entry.insert(0, "")
        text['text']="The value of "+ str(Name) +" is $" + find_stock_value(varenter)
        text.pack()
        entry.pack_forget()
        result = requests.get("https://www.marketwatch.com/investing/stock/" + str(Name))
        soup = BeautifulSoup(result.content, "html.parser")
        for link in soup.find_all("td", class_="table__cell not-fixed positive"):
            Stock_Price = link.text
        for link in soup.find_all("td", class_="table__cell not-fixed negative"):
            Stock_Price = link.text
        negative=False
        for letter in Stock_Price:
            if letter=='-':
                negative=True
                break
        if negative==True:
            text['text']+=' and has fallen ' + Stock_Price.lstrip('-') + str(" since opening.")
        else:
            text['text']+=' and has risen ' + str(Stock_Price) + str(" since opening.")
def mlearning2():
    global enterallowed
    enterallowed=True
    global option
    option='mlearning'
    global w
    text['text']='Type the abbreviation of the stock name: '
    text.pack()
    entry.pack(side=tk.TOP,padx=2,pady=22)
    frame.pack_forget()
    frame2.pack(side=tk.BOTTOM)
    b7.pack(side=tk.LEFT,padx=2)

def mlearnenter(Name):
    if entry.get() != '':
        global varenter
        Name=Name.upper()
        varenter=entry.get()
        varenter=varenter.upper()
        entry.delete(0, 'end')
        entry.insert(0, "")
        X = np.array([[24.63, -0.87], [80.88, 0.37], [15.49, -2.06], [15.7, 0.74], [64.6, -0.69], [1.37, -0.55], [8.85, -0.48], [44.24, -0.209], [81.91, 2.65], [134.1, -0.16], [53.47, -0.25],
                  [128.04, -0.06], [5.72, 0.23], [196.54, -0.244], [54.47, -0.66], [54.85, -0.22], [24.75, -0.62], [15.64, -0.35], [90.4, -0.49], [1.32, -0.63], [8.42, -0.88],  
                  [35.16, -0.48], [85.25, -0.45], [30.36, -0.4], [416, 0.25], [60.22, 0.09], [1429.95, 0.14], [12.41, 0.47], [45.33, -2.39], [210, -2.03], [22.26, 1.04],
                  [236, 4.42], [71.17, -2.51], [93.86, -2.06], [65.22, -0.53], [29.13, -3.06], [122, -0.95], [0.15, -24.12],

                  
                  [166.52, 0.04], [165.71, -0.87], [68.11, 0.23], [1118.62, -0.0003], [104.95, -0.13], [34, -0.05], [29.75, -0.17], [339.75, 0.26], [279.06, 0.26],
                  [89.59, 0.12], [110.11, 0.79], [50.5, -0.52], [74.67, -0.1], [48.7, -0.04], [37.93, -0.38], [33.08, -0.48], [34.42, -0.22], [527.2, 0.69], [1836.96, -0.01],
                  [244, -0.05], [1626.23, 0.14], [271.7, -0.02], [144.73, -0.38], [2.84, -0.15], [0.84, -0.24], [5.2, 0.19], [81.51, -0.5], [114.3, -3.96], [551.16, -1.49], [28.84, 0.52],
                  [140.15, 3.81], [48.73, 3.42], [39.6, 3.61], [24.51, 0.41], [118.37, 1.25], [49.65, 2.1]])
        Y = np.array([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1])

        clf = GaussianNB()
        clf.fit(X, Y)

        result = requests.get("https://www.marketwatch.com/investing/stock/" + str(Name))

        soup = BeautifulSoup(result.content, "html.parser")
        Stock_Change_1 = ""
        NewValue = ""
        
        for link in soup.find_all("bg-quote", class_="value"):
            Stock_Val = link.text
        for link in soup.find_all("span", class_="value"):
            Stock_Val = link.text

        for link in soup.find_all("span", class_="change--percent--q"):
            Stock_Change = link.text
        for link in soup.find_all("bg-quote", class_="change--percent--q"):
            Stock_Change = link.text
        for Letter in Stock_Val:
            if Letter.isdigit() == True:
                NewValue = str(NewValue) + str(Letter)
            elif str(Letter) == str("."):
                NewValue = str(NewValue) + str(Letter)
                
        Stock_Val = float(NewValue)
        
            
        for Char in Stock_Change:
            if Char != "%":
                Stock_Change_1 = str(Stock_Change_1) + str(Char)
                
        Prediction = clf.predict([[float(Stock_Val), float(Stock_Change_1)]])
        b7.pack_forget()
        entry.pack_forget()
        Stock_Val = str(Stock_Val)
        Stock_Change = str(Stock_Change)
    if str(Prediction) == str("[0]"):
        return ("This is predicted to be a BAD investment because: " + "\n" + "Percent Change: " + Stock_Change + "\n" + "Price of Stock: $" + Stock_Val + "\n" + "\n" + "\n" + "NOTE: These % changes are for the whole year to determine the best possible investments")
    elif str(Prediction) == str("[1]"):
        return ("This is predicted to be a GOOD investment because: "+ "\n" + "Percent Change: " + Stock_Change + "\n" +"Price of Stock: $" + Stock_Val + "\n" + "\n" + "\n" + "NOTE: These % changes are for the whole year to determine the best possible investments")
     

def graph():
    global varenter
    varenter=varenter.upper()
    entry.delete(0, 'end')
    entry.insert(0, "")
    Stock_Name=str(varenter)
    request=requests.get("https://charting.nasdaq.com/ext/charts.dll?2-1-14-0-0-512-03NA000000" + str(Stock_Name) + "-&SF:1|5-BG=FFFFFF-BT=0-HT=395--XTBL-")
    soup = BeautifulSoup(request.content, "html.parser")
    counter = 0
    vcounter = 127
    pstring = []
    vstring = []
    pstring1 = []
    bstring = ""
    for item in soup.find_all("td", class_="DrillDownData"):
        counter += 1
        if counter % 4 == 1 and counter < 1000:
            pstring.append(item.text)
            vcounter -= 1
            vstring.append(vcounter)
    for string in pstring:
        bstring = float(string)
        pstring1.append(float(string))
    plt.plot(vstring, pstring1)
    plt.show()
   

root = tk.Tk()
import sys, os
#print('sys.argv[0] =', sys.argv[0])             
pathname = os.path.dirname(sys.argv[0])      
eval('root.iconbitmap(r"'+str(pathname)+'\Logo.ico")')
pathname2=str(pathname)+'\Logo.png'
im="logo = tk.PhotoImage(file=r'"+str(pathname2)+"')"
exec(str(im))
#logo = tk.PhotoImage(file=r'C:\Users\n6s1\OneDrive\Desktop\Zhiwei\StockSim\Logo.png')
root.geometry("800x550")
root.title("StockSim")
root.configure(background='black')
sidebar = tk.Frame(root)
sidebar.pack(anchor='nw')
w1 = tk.Label(root, image=logo,background='black', highlightbackground="green", highlightcolor="green", highlightthickness=30).pack()
mon = tk.Label(sidebar, text='$'+str(Money), background='black', foreground='white',font=('calibri', 16, 'bold'))
mon.pack()
supertext="Welcome to The Stock Market Simulator! Press a button below to manage stocks."
varenter=''
option=''
text = tk.Label(root, text=supertext, wraplength=700, background='black', foreground='white', font=('calibri', 16))
text.pack(side=tk.TOP)
entry = tk.Entry(root, width = 10,background='black',foreground='white')
entry.pack_forget()
entry.bind('<Return>', enter2)
frame = tk.Frame(root, background='black')
frame.pack()
frame2 = tk.Frame(root, background='black')
frame2.pack()
#b1 = tk.Button(frame, text="Search", bg='blue',fg="white",command=search2,width = 10)
#b1.pack(side=tk.LEFT,padx=2)
b5 = tk.Button(frame, text="Search",bg='#4C7BD1', fg="white",command=find2,width = 15)
b5.pack(side=tk.LEFT,padx=2)
b2 = tk.Button(frame, text="Buy",bg='#4C7BD1', fg="white",command=buy2,width = 15)
b2.pack(side=tk.LEFT,padx=2)
b3 = tk.Button(frame, text="Sell",bg='#4C7BD1', fg="white",command=sell2,width = 15)
b3.pack(side=tk.LEFT,padx=2)
b4 = tk.Button(frame, text="Inv",bg='#4C7BD1', fg="white",command=inv2,width = 15)
b4.pack(side=tk.LEFT,padx=2)
b6 = tk.Button(frame, text="Top Stocks",bg='#4C7BD1', fg="white",command=high2,width = 15)
b6.pack(side=tk.LEFT,padx=2)
b9 = tk.Button(frame, text="Machine Opinion",bg='#4C7BD1', fg="white",command=mlearning2,width = 15)
b9.pack(side=tk.LEFT,padx=2)

b7 = tk.Button(frame2, text="Enter",bg='#4C7BD1', fg="white",command=enter,width = 15,height = 2)
b7.pack(side=tk.LEFT,padx=2)
b8 = tk.Button(frame2, text="Back",bg='#4C7BD1', fg="white",command=back,width = 15,height = 2)
b8.pack(side=tk.RIGHT,padx=2)
b10 = tk.Button(frame2, text="Graph",bg='#4C7BD1', fg="white",command=graph,width = 15,height = 2)
b10.pack(side=tk.RIGHT,padx=2)
frame.pack(side=tk.BOTTOM)
frame2.pack_forget()
b10.pack_forget()
root.mainloop()






