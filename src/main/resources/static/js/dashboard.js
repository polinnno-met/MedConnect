
var remainingAppointmentsCtx = document.getElementById('remainingAppointmentsChart');
if (remainingAppointmentsCtx) {
    console.log('Remaining Appointments Canvas element found'); // Debug log
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

// // Calendar
// var calendarEl = document.getElementById('calendar');
// var patientsCard = document.getElementById('patientsCard');
//
// // Get the height of the patients card
// var patientsCardHeight = patientsCard.offsetHeight;
// console.log(patientsCardHeight.valueOf());
//
// // Set the height of the calendar to match the height of the patients card
// calendarEl.style.height = patientsCardHeight + 'px';
//
// var calendar = new FullCalendar.Calendar(calendarEl, {
//     initialView: 'dayGridMonth',
//     headerToolbar: {
//         left: 'prev,next',
//         center: 'title',
//         right: ''
//     },
//     titleFormat: { // Will display the full year
//         year: 'numeric',
//         month: 'long'
//     },
//     dayHeaders: true, // Will display day headers (Mon, Tue, etc.)
//     dayHeaderFormat: { weekday: 'short' }, // Short format for days (Mon, Tue, etc.)
//     height: patientsCardHeight - 10, // Set the height for the calendar
//     selectable: true,
//     expandRows: false,
//     themeSystem: 'bootstrap', // Use the bootstrap theme
//     displayEventTime: false, // Do not display event time
//     displayEventEnd: false,
//
// });
//
// console.log("calendar created")
//
// calendar.render();
