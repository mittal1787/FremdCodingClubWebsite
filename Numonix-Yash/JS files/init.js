var firebaseConfig = {
    apiKey: "AIzaSyA28GUUOQfSR7Zthsj8nC2mdpdED8nk-fs",
    authDomain: "numonix-4a97b.firebaseapp.com",
    projectId: "numonix-4a97b",
    storageBucket: "numonix-4a97b.appspot.com",
    messagingSenderId: "232140423715",
    appId: "1:232140423715:web:d32fc26855b6b225d7a796"
  };
  
  firebase.initializeApp(firebaseConfig);
  const auth = firebase.auth();
  const db = firebase.firestore();