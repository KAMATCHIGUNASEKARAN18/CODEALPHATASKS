import java.util.ArrayList;
import java.util.Scanner;

public class GradeTracker {
    private ArrayList<Double> grades;

    public GradeTracker() {
        grades = new ArrayList<>();
    }

    // Method to add a grade
    public void addGrade(double grade) {
        grades.add(grade);
    }

    // Method to calculate the average grade
    public double calculateAverage() {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return grades.size() == 0 ? 0 : sum / grades.size();
    }

    // Method to find the highest grade
    public double getHighestGrade() {
        double highest = Double.MIN_VALUE;
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return grades.size() == 0 ? 0 : highest;
    }

    // Method to find the lowest grade
    public double getLowestGrade() {
        double lowest = Double.MAX_VALUE;
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return grades.size() == 0 ? 0 : lowest;
    }

    public static void main(String[] args) {
        GradeTracker tracker = new GradeTracker();
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            System.out.println("Enter a grade (or type 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                done = true;
            } else {
                try {
                    double grade = Double.parseDouble(input);
                    tracker.addGrade(grade);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid grade.");
                }
            }
        }

        System.out.println("Average Grade: " + tracker.calculateAverage());
        System.out.println("Highest Grade: " + tracker.getHighestGrade());
        System.out.println("Lowest Grade: " + tracker.getLowestGrade());
    }
}
