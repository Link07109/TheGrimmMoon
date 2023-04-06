package main.java;

public class Choice {
    private String prompt;
    private ChoiceOption[] options;

    public Choice(String prompt, ChoiceOption[] options) {
        this.prompt = prompt;
        this.options = options;
    }

    public void pickOption(ChoiceOption option) {
        option.consequence();
    }

    public ChoiceOption[] getOptions() {
        return options;
    }

    public String getPrompt() {
        return prompt;
    }
}
