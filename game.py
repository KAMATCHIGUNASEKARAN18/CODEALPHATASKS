
import random

def get_computer_choice():
    choices = ["rock", "paper", "scissors"]
    return random.choice(choices)

def get_winner(player_choice, computer_choice):
    if player_choice == computer_choice:
        return "draw"
    elif (player_choice == "rock" and computer_choice == "scissors") or \
         (player_choice == "scissors" and computer_choice == "paper") or \
         (player_choice == "paper" and computer_choice == "rock"):
        return "player"
    else:
        return "computer"

def print_board(player_score, computer_score):
    print("\n--- Scoreboard ---")
    print(f"Player: {player_score} | Computer: {computer_score}")
    print("-------------------")

def main():
    player_score = 0
    computer_score = 0
    rounds = 3
    
    for round in range(rounds):
        print(f"\nRound {round + 1} of {rounds}")
        player_choice = input("Choose rock, paper, or scissors: ").strip().lower()
        
        if player_choice not in ["rock", "paper", "scissors"]:
            print("Invalid choice. Please choose rock, paper, or scissors.")
            continue
        
        computer_choice = get_computer_choice()
        print(f"Computer chose: {computer_choice}")
        
        result = get_winner(player_choice, computer_choice)
        
        if result == "player":
            print("You win this round!")
            player_score += 1
        elif result == "computer":
            print("Computer wins this round!")
            computer_score += 1
        else:
            print("This round is a draw!")
        
        print_board(player_score, computer_score)
    
    print("\n--- Final Results ---")
    if player_score > computer_score:
        print("Congratulations, you win the game!")
    elif player_score < computer_score:
        print("Computer wins the game. Better luck next time!")
    else:
        print("The game is a draw!")

if __name__ == "__main__":
    main()


