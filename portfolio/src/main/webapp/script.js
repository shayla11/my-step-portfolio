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

/**
 * Adds a random Movie Quote to the page.
 */
function addRandomMovieQuote() {
    const movieQuotes =
        ['I dont know how to make paint! - Diary of a Wimpy Kid:Roderick Rules',
            'Yo Adrian! - Rocky',
            'This guy is a few tacos short of a combination plate. - Good Burger',
            'They carried my mothers groceries outta respect. - Goodfellas',
            'Why dont you make like a tree and get outta here! - Back to the Future II',
            'No capes! - The Incredibles',
            'Put that thing back where it came from or so help me! - Monsters Inc.',
            'This isnt flying. Its falling.. with style! - Toy Story',
            'Do. or do not. There is not try. - Star Wars: The Empire Strikes Back',
            'All bubble-blowing babies will be beaten senseless by every able-bodied patron in the bar - The Spongebob Squarepants Movie',
            'It is not a donut hole at all, but a smaller donut with its own hole - Knives Out',
            'Evaporate tall person! - High School Musical',
            'If we are going to perform Inception, we need imagination. - Inception'];

    // Pick a random greeting.
    const movieQuote = movieQuotes[Math.floor(Math.random() * movieQuotes.length)];

    // Add it to the page.
    const movieQuoteContainer = document.getElementById('quote-container');
    movieQuoteContainer.innerText = movieQuote;
}

/**
 * Loads comments that have been made from user input
 */
function getComments() {
    fetch('/data').then(response => response.json()).then((tasks) => {
        const messageList = document.getElementById('comment-container');
        messageList.innerHTML = '';
        for (let i = 0; i < tasks.length; i++) {
            messageList.appendChild(createListElement(tasks[i].name + ' : ' + tasks[i].text));
        }
    });
}

function createListElement(fullComment) {
    const liElement = document.createElement('ul');
    liElement.className = "comment-item";
    liElement.innerText = fullComment;
    return liElement;
}

function deleteComments() {
    const messageList = document.getElementById("comment-container");
    while (messageList.firstChild) {
        messageList.removeChild(messageList.firstElementChild);
    }
}
