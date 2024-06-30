// Sidebar toggle
document.getElementById('sidebarToggle').addEventListener('click', function() {
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.querySelector('.main-content');
    const sidebarHeader = document.querySelector('.sidebar-header .header-title');

    sidebar.classList.toggle('active');
    mainContent.classList.toggle('sidebar-active');

    if (sidebar.classList.contains('active')) {
        sidebarHeader.textContent = 'MedConnect';
    } else {
        sidebarHeader.textContent = 'M';
    }

    var patientsCard = document.getElementById('patientsCard');
    var patientsCardHeight = patientsCard.offsetHeight;

});


function logout() {
    console.log("Logout function called in js");

    localStorage.removeItem('idToken');
    localStorage.removeItem('userRole');
    document.getElementById('logoutForm').submit();
}

feather.replace();

function goToDashboard() {
    var idToken = localStorage.getItem('idToken');
    if (idToken) {
        fetch('/dashboard', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + idToken
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.text();
        }).then(html => {
            document.write(html);
        }).catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
            alert('Failed to fetch dashboard data');
            window.location.href = "/404";
        });
    } else {
        alert('User not authenticated. Please log in first.');
    }
}



$('.select2').select2();
