fetchDashboardData();

function fetchDashboardData() {
    console.log("function entered")
    var idToken = localStorage.getItem('idToken');
    if (idToken) {
        fetch('/dashboard/data', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + idToken
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        }).then(data => {
            renderDashboard(data);
        }).catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
            alert('Failed to fetch dashboard data');
            window.location.href = "/404";
        });
    } else {
        alert('User not authenticated. Please log in first.');
        window.location.href = "/login";
    }
}