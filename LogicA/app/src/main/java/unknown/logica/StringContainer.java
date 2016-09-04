package unknown.logica;

import android.content.res.Resources;

/**
 *Created by Tim on 09/02/16.
 */
public class StringContainer {

    public static String player1_string;               //Player 1
    public static String player2_string;               //Player 2
    public static String submit_string;                //Submit
    public static String correct_answer_string;        //Congratulation! Correct Answer!
    public static String incorrect_answer_string;      //Incorrect Answer
    public static String answer_string;                //Answer
    public static String game_pause_string;            //Game Paused
    public static String game_over_string;             //Game Over
    public static String next_game_string;             //Next Game
    public static String quit_string;                  //Quit
    public static String click_string;                 //Click
    public static String hint_input_title_string;      //Enter your Input for Hint:
    public static String hint_input_message_string;    //Integer range from 0 to 9999
    public static String invalid_value_string;         //Invalid Value
    public static String game_start_string;            //Game Start
    public static String round_mode_description_string;//The game ends when it reaches the target round
    public static String score_mode_string;            //Score Mode
    public static String round_mode_string;            //Round Mode

    public static void initializeStrings(Resources res){
        player1_string = res.getString(R.string.player1);
        player2_string = res.getString(R.string.player2);
        submit_string = res.getString(R.string.submit_label);
        correct_answer_string = res.getString(R.string.correct_answer_label);
        incorrect_answer_string = res.getString(R.string.incorrect_answer_label);
        answer_string = res.getString(R.string.answer_label);
        game_pause_string = res.getString(R.string.game_pause_label);
        game_over_string = res.getString(R.string.game_over_label);
        next_game_string = res.getString(R.string.next_game_label);
        quit_string = res.getString(R.string.quit_label);
        click_string = res.getString(R.string.click_label);
        hint_input_title_string = res.getString(R.string.hint_input_title);
        hint_input_message_string = res.getString(R.string.hint_input_message);
        invalid_value_string = res.getString(R.string.invalid_value_label);
        game_start_string = res.getString(R.string.start_game_label);
        round_mode_description_string = res.getString(R.string.round_mode_description);
        score_mode_string = res.getString(R.string.score_mode_title);
        round_mode_string = res.getString(R.string.round_mode_title);
    }
}
