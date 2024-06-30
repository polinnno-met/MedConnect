var billStatusCtx = document.getElementById('billStatusChart');
if (billStatusCtx) {
    var billStatusCounts = JSON.parse(document.getElementById('billStatusCounts').value);

    if (Object.keys(billStatusCounts).length > 0) {
        new Chart(billStatusCtx.getContext('2d'), {
            type: 'bar',
            data: {
                labels: Object.keys(billStatusCounts),
                datasets: [{
                    data: Object.values(billStatusCounts),
                    backgroundColor: [
                        'rgba(145,145,145,0.2)',
                        'rgba(255,171,13,0.2)',
                        'rgba(99,176,54,0.2)',
                        'rgba(217,78,78,0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgb(141,141,141)',
                        'rgb(235,145,54)',
                        'rgb(91,190,84)',
                        'rgb(241,93,93)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                indexAxis: 'y',
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Bill Status Distribution'
                    }
                },
                scales: {
                    x: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
} else {
    console.log('Bill Status Canvas element not found');
}
