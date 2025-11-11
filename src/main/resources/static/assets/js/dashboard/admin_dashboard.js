var chartprofit = new ApexCharts(document.querySelector("#profitmonthly"), optionsprofit);
chartprofit.render();

// overview chart
var optionsoverview = {
    series: [ {
        name: 'Earning',
        type: 'area',
        data: [44, 55, 35, 50, 67, 50, 55, 45, 32, 38, 45]
    },
        {
            name: 'Order',
            type: 'area',
            data: [35, 30, 23, 40, 50, 35, 40, 52, 67, 50, 55]
        },
        {
            name: 'Refunds',
            type: 'area',
            data: [25, 20, 15, 25, 32, 20, 30, 35, 23, 30, 20]
        },
    ],
    chart: {
        height: 300,
        type: 'line',
        stacked: false,
        toolbar: {
            show: false
        },
        dropShadow: {
            enabled: true,
            top: 2,
            left: 0,
            blur: 4,
            color: '#000',
            opacity: 0.08
        }
    },
    stroke: {
        width: [2, 2, 2],
        curve: 'smooth'
    },
    grid: {
        show: true,
        borderColor: 'var(--chart-border)',
        strokeDashArray: 0,
        position: 'back',
        xaxis: {
            lines: {
                show: true
            }
        },
        padding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
        },
    },
    plotOptions: {
        bar: {
            columnWidth: '50%'
        }
    },
    colors: ["#7064F5", "#54BA4A", "#FF3364"],
    fill: {
        type: 'gradient',
        gradient: {
            shade: 'light',
            type: "vertical",
            opacityFrom: 0.4,
            opacityTo: 0,
            stops: [0, 100]
        }
    },
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
        'Aug', 'Sep', 'Oct', 'Nov'
    ],
    markers: {
        discrete: [{
            seriesIndex: 0,
            dataPointIndex: 2,
            fillColor: '#7064F5',
            strokeColor: 'var(--white)',
            size: 5,
            sizeOffset: 3
        }, {
            seriesIndex: 1,
            dataPointIndex: 2,
            fillColor: '#54BA4A',
            strokeColor: 'var(--white)',
            size: 5,
        },
            {
                seriesIndex: 2,
                dataPointIndex: 2,
                fillColor: '#FF3364',
                strokeColor: 'var(--white)',
                size: 5,
            },
            {
                seriesIndex: 0,
                dataPointIndex: 5,
                fillColor: '#7064F5',
                strokeColor: 'var(--white)',
                size: 5,
                sizeOffset: 3
            }, {
                seriesIndex: 1,
                dataPointIndex: 5,
                fillColor: '#54BA4A',
                strokeColor: 'var(--white)',
                size: 5,
            },
            {
                seriesIndex: 2,
                dataPointIndex: 5,
                fillColor: '#FF3364',
                strokeColor: 'var(--white)',
                size: 5,
            },
            {
                seriesIndex: 0,
                dataPointIndex: 9,
                fillColor: '#7064F5',
                strokeColor: 'var(--white)',
                size: 5,
                sizeOffset: 3
            }, {
                seriesIndex: 1,
                dataPointIndex: 9,
                fillColor: '#54BA4A',
                strokeColor: 'var(--white)',
                size: 5,
            },
            {
                seriesIndex: 2,
                dataPointIndex: 9,
                fillColor: '#FF3364',
                strokeColor: 'var(--white)',
                size: 5,
            },
        ],
        hover: {
            size: 5,
            sizeOffset: 0
        }
    },
    xaxis: {
        type: 'category',
        tickAmount: 4,
        tickPlacement: 'between',
        tooltip: {
            enabled: false,
        },
        axisBorder: {
            color: 'var(--chart-border)',
        },
        axisTicks: {
            show: false
        }
    },
    legend: {
        show: false,
    },
    yaxis: {
        min: 0,
        tickAmount: 6,
        tickPlacement: 'between',
    },
    tooltip: {
        shared: false,
        intersect: false,
    },
    responsive: [{
        breakpoint: 1200,
        options: {
            chart: {
                height: 250,
            }
        },
    }]

};

var chartvisitor = new ApexCharts(document.querySelector("#visitor-chart"), optionsvisitor);
chartvisitor.render();