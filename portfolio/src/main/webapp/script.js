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
Your goal this week is to customize your portfolio page. What that means is up to you, but here are a few ideas to get you started:
Add a couple paragraphs explaining your background and what makes you you.
Add a list of projects you've worked on.
Add a link to your LinkedIn and GitHub profiles.
Add images! Create a gallery of your favorite places, or selfies, or pictures of your pets.
Add a couple paragraphs explaining what you enjoy photographing.
Add some blog posts explaining one of your hidden talents.

This is your portfolio page, so make it your own! 
*/

/**
 * Adds a random greeting to the page.
 */
function addRandomMovieQuote() {
  const movieQuotes =
      ['I dont know how to make paint!', 'Yo Adrian!', 'This guy is a few tacos short of a combination plate.', 'They carried my mothers groceries outta respect.'];

  // Pick a random greeting.
  const movieQuote = movieQuotes[Math.floor(Math.random() * movieQuotes.length)];

  // Add it to the page.
  const movieQuoteContainer = document.getElementById('movie-quote-container');
  movieQuoteContainer.innerText = movieQuote;
}
