
var remainingAppointmentsCtx = document.getElementById('remainingAppointmentsChart');
if (remainingAppointmentsCtx) {
    remainingAppointmentsCtx = remainingAppointmentsCtx.getContext('2d');
    new Chart(remainingAppointmentsCtx, {
        type: 'doughnut',
        data: {
            labels: ['Completed', 'Scheduled'],
            datasets: [{
                data: [parseInt(document.getElementById('completedAppointments').value), parseInt(document.getElementById('scheduledAppointments').value)],
                backgroundColor: ['rgb(124, 143, 196)', 'rgba(124, 143, 196,0.5)'],
                borderWidth: 0
            }]
        },
        options: {
            borderWidth: 0,
            responsive: true,
            animation: {
                animateRotate : true
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        usePointStyle: true,
                    }
                },
                tooltip: {
                    enabled: true,
                    mode: 'index',
                    intersect: false
                }
            }
        }
    });
} else {
    console.log('Remaining Appointments Canvas element not found');
}
