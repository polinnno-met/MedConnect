// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyCnOzzlp4v7_arUneoK3ZPxx4n-oJ7FnJ4",
    authDomain: "cs450-5859.firebaseapp.com",
    projectId: "cs450-5859",
    storageBucket: "cs450-5859.appspot.com",
    messagingSenderId: "151931224823",
    appId: "1:151931224823:web:9efe844600eb230d1e49f2",
    measurementId: "G-M5E63DF98M"
};

firebase.initializeApp(firebaseConfig);
function loginWithGoogle() {
    var provider = new firebase.auth.GoogleAuthProvider();
    firebase.auth().signInWithPopup(provider)
        .then((result) => {
            result.user.getIdToken().then((idToken) => {
                console.log(idToken)
                fetch('/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ idToken: idToken })
                }).then(response => {
                    if (response.ok) {

                        window.location.href = "/dashboard";
                    } else {
                        alert('Login failed. Please try again.');
                    }
                });
            });
        })
        .catch((error) => {
            console.error("Login with Google failed:", error);
            alert('Login with Google failed: ' + error.message);
        });
}

function loginWithEmail() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    firebase.auth().signInWithEmailAndPassword(email, password)
        .then((userCredential) => {
            userCredential.user.getIdToken().then((idToken) => {
                fetch('/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ idToken: idToken })
                }).then(response => {
                    if (response.ok) {
                        window.location.href = "/dashboard";
                    } else {
                        alert('Login failed. Please try again.');
                    }
                });
            });
        })
        .catch((error) => {
            console.error("Login failed:", error);
            alert('Login failed: ' + error.message);
        });
}

