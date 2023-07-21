window.onload = function () {

    console.log("Passed value: ", passed)
    console.log("Untested value: ", untested)
    console.log("Blocked value: ", blocked)
    console.log("Retest value: ", retest)
    console.log("Failed value: ", failed)

    Highcharts.chart('container', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Status Overview',
            align: 'left'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                colors: [
                    '#00FF00',
                    '#a9093a',
                    '#737373',
                    '#474747',
                    '#b99109',
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            name: 'Number of test case',
            colorByPoint: true,
            data: [{
                name: 'Passed',
                y: passed
            }, {
                name: 'Failed',
                y: failed
            },  {
                name: 'Untested',
                y: untested
            }, {
                name: 'Blocked',
                y: blocked
            }, {
                name: 'Retest',
                y: retest
            }]
        }]
    })
};

// // Load the Visualization API and the corechart package.
// google.charts.load('current', {'packages': ['corechart']});
//
// // Set a callback to run when the Google Visualization API is loaded.
// google.charts.setOnLoadCallback(drawChart);
//
// // Callback that creates and populates a data table,
// // instantiates the pie chart, passes in the data and
// // draws it.
// function drawChart() {
//
//     // Create the data table.
//     var data = new google.visualization.DataTable();
//     data.addColumn('string', 'Topping');
//     data.addColumn('number', 'Slices');
//     data.addRows([
//         ['Mushrooms', 2],
//         ['Onions', 3],
//         ['Olives', 7],
//         ['Zucchini', 9],
//         ['Pepperoni', 10],
//     ]);
//
//     // Set chart options
//     var options = {
//         'title': 'Test Run Chart',
//         'width': 500,
//         'height': 400
//     };
//
//     // Instantiate and draw our chart, passing in some options.
//     var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
//     chart.draw(data, options);
// }