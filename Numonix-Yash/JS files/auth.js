function signUp()
{
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    console.log(email + " and " + password)
    
    auth.createUserWithEmailAndPassword(email, password).then(() => {
        console.log('signed up: ' + email);
        location.href = "Create.html";
    }).catch(e => alert(e.message));
    
}


function signIn()
{
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    auth.signInWithEmailAndPassword(email, password).then(() => {
        console.log('signed in: ' + email);
        location.href = "Create.html";
    }).catch(e => alert(e.message));
}

function signOut()
{
    auth.signOut();
    console.log("Signed Out")
}

auth.onAuthStateChanged(user => {
    if (user) {
        toggleData(true, user);
    }
    else {
        toggleData(false);
    }
 })