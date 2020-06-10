// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawGameChart);
google.charts.setOnLoadCallback(drawInputChart);

/** Creates a chart and adds it to the page. */
function drawGameChart() {
    const data = new google.visualization.arrayToDataTable([
        ['Game', 'Hours', { role: 'annotation' }],
        ['Animal Crossing: New Leaf', 415, '415'],
        ['Fantasy Life', 278, '278'],
        ['Animal Crossing: New Horizons', 190, '190'],
        ['Stardew Valley', 105, '105'],
        ['Mario Kart 7', 79, '79'],
        ['Splatoon 2', 65, '65'],
        ['Paper Mario: Sticker Star', 61, '61'],
        ['Lego Rock Band DS', 50, '50'],
        ['Tetris 99', 25, '25']
    ]);

    const options = {
        'title': 'Play Activity',
        'width': 650,
        'height': 650,
        'hAxis': { title: 'Games' },
        'vAxis': { title: 'Hours Played' }
    };

    const chart = new google.visualization.ColumnChart(
        document.getElementById('game-barchart-container'));
    chart.draw(data, options);
}

function drawInputChart() {
    fetch('/spider-data').then(response => response.json())
        .then((spiderVotes) => {
            const data = new google.visualization.DataTable();
            data.addColumn('string', 'spiderman');
            data.addColumn('number', 'Votes');
            Object.keys(spiderVotes).forEach((spider) => {
                data.addRow([spider, spiderVotes[spider]]);
            });

            const options = {
                'title': 'The Best Spiderman',
                'width': 600,
                'height': 500,
            };

            const chart = new google.visualization.PieChart(
                document.getElementById('spider-piechart-container'));
            chart.draw(data, options);
        });
}

function drawAllCharts() {
    drawInputChart();
    drawGameChart();
}
