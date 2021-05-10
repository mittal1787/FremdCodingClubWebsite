function toggleData(loggedIn, user) {
    if (loggedIn) {
        notLoggedContainer.style.display = "none";
        loggedContainer.style.display = "flex";
        loggedMain.style.display = "flex";
        signedHeader.innerHTML = "Signed in as: " + user.email;
    } else {
        notLoggedContainer.style.display = "flex";
        loggedContainer.style.display = "none";
        loggedMain.style.display = "none";
    }
    
}