package org.example.shop.cli.core;

public class CommandItem implements MenuItem {
    private final Command cmd;

    public CommandItem(Command cmd) {
        this.cmd = cmd;
    }

    @Override
    public String label() {
        return cmd.name() + " — " + cmd.description();
    }

    @Override
    public void trigger(MenuNavigator nav, Context ctx) throws Exception {
        System.out.println("Usage: " + cmd.usage());
        System.out.println("> ");

        if (!ctx.in.hasNextLine()) {
            return;
        }

        String line = ctx.in.nextLine().trim();
        String[] args = line.split(" ");
        cmd.execute(args, ctx);
    }
}
