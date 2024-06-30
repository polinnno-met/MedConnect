
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

