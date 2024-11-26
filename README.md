# README

All repos need a README! You can find lots of examples online - but don't worry about it being too fancy. Just make sure
it's clear what your project is, how to run it locally and how to deploy it.

## Some thoughts

### DB

- Your live database is in the git history, look into how to remove a commit from the history - you might get away with
  just squashing those commit.
- It is also publicly accessible as shown in the png I've added. You want to lock your database down behind some
  security groups so it is only accessible by your container instance.

### Code

- package Config should be lowercase
- Maybe consider something like Lombok to reduce boilerplate code in your POJOs (Plain Old Java Objects)
- Maybe consider something like sonarlint to help with code quality and make sure to format all your files consistently