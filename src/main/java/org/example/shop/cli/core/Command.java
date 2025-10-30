package org.example.shop.cli.core;

public interface Command {
    String name();

    String description();

    String usage();

    void execute(String[] args, Context ctx) throws Exception;
}
