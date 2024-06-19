
var ctx = document.getElementById('myChart');
if (ctx) {
    console.log('Canvas element found'); // Debug log
    ctx = ctx.getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [
                {
                    label: '2023',
                    data: [1200, 1500, 1700, 2100, 2400, 2800, 3200, 3000, 2700, 2900, 3100, 3500],
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderWidth: 2,
                    pointBackgroundColor: 'rgba(54, 162, 235, 1)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(54, 162, 235, 1)',
                    tension: 0.4 // Smooth line
                },
                {
                    label: '2022',
                    data: [800, 900, 1100, 1200, 1400, 1600, 1900, 2100, 2300, 2500, 2700, 3000],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderWidth: 2,
                    pointBackgroundColor: 'rgba(75, 192, 192, 1)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(75, 192, 192, 1)',
                    borderDash: [5, 5], // Dashed line
                    tension: 0.4 // Smooth line
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        usePointStyle: true,
                    }
                },
                tooltip: {
                    enabled: true,
                    mode: 'index',
                    intersect: false
                }
            },
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Month'
                    }
                },
                y: {
                    display: true,
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Value'
                    }
                }
            }
        }
    });
} else {
    console.log('Canvas element not found'); // Debug log
}



var remainingAppointmentsCtx = document.getElementById('remainingAppointmentsChart');
if (remainingAppointmentsCtx) {
    console.log('Remaining Appointments Canvas element found'); // Debug log
    remainingAppointmentsCtx = remainingAppointmentsCtx.getContext('2d');
    new Chart(remainingAppointmentsCtx, {
        type: 'doughnut',
        data: {
            labels: ['Scheduled', 'Completed'],
            datasets: [{
                data: [parseInt(document.getElementById('scheduledAppointments').value), parseInt(document.getElementById('completedAppointments').value)],
                backgroundColor: ['rgba(75, 192, 192, 1)', 'rgba(54, 162, 235, 1)']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
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
    console.log('Remaining Appointments Canvas element not found'); // Debug log
}

    feather.replace();
