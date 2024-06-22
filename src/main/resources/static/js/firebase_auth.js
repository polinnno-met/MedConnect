const firebaseConfig = {
    apiKey: "AIzaSyCnOzzlp4v7_arUneoK3ZPxx4n-oJ7FnJ4",
    authDomain: "cs450-5859.firebaseapp.com",
    projectId: "cs450-5859",
    storageBucket: "cs450-5859.appspot.com",
    messagingSenderId: "151931224823",
    appId: "1:151931224823:web:9efe844600eb230d1e49f2",
    measurementId: "G-M5E63DF98M"
};

if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
} else {
    firebase.app();
}

firebase.auth().onAuthStateChanged(user => {
    if (!user) {
        console.log('User not authenticated');
        alert('User not authenticated. Please log in first.');
        window.location.href = "/";
    }
});