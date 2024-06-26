    var ctx = document.getElementById('ageDistributionChart').getContext('2d');
    var ageDistributionChart = new Chart(ctx, {
    type: 'bar',
    data: {
    labels: ['20-30', '31-40', '41-50', '51-60'],
    datasets: [{
    label: 'Number of Patients',
    data: [0, 2, 2, 1], // assuming age ranges based on table data
    backgroundColor: '#42A5F5',
}]
},
    options: {
    responsive: true,
    title: {
    display: true,
    text: 'Age Distribution'
},
    scales: {
    yAxes: [{
    ticks: {
    beginAtZero: true
}
}]
}
}
});

