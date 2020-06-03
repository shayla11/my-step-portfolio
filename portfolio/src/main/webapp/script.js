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
        ['I dont know how to make paint!', 'Yo Adrian!', 'This guy is a few tacos short of a combination plate.', 'They carried my mothers groceries outta respect.'];

    // Pick a random greeting.
    const movieQuote = movieQuotes[Math.floor(Math.random() * movieQuotes.length)];

    // Add it to the page.
    const movieQuoteContainer = document.getElementById('quote-container');
    movieQuoteContainer.innerText = movieQuote;
}

async function getRandomMessage() {
    fetch('/data').then(response => response.json()).then((comments) => {
        document.getElementById('quote-container').innerText = comments;
        console.log(comments);
    });
}
