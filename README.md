# Rhythm_Game

# Running the game:

The game will prompt you for 3 successive inputs
1. Enter the name of the .txt file that contains your notemap (if the name includes '.txt' you have to include it as well)
2. Enter the name of the .wav file of the song that corresponds to the previously entered notemap. Leaving this input blank will continue the game silently.
3. Enter the speed at which you want the notes to appear (6-12). Higher numbers mean notes will appear later and approach faster. Lower numbers mean notes will appear sooner and approach slower. This does not affect the speed of the song.

# Formatting song maps:

Format tap notes as follows:

starttime,key

Format hold notes as follows:

starttime-endttime,key

The key must be one of the 6 keys in-game (q,w,e,i,o,p)

Endttime must obviously come after starttime

White spaces and blank lines will be trimmed. Any other errors will throw an exception.
