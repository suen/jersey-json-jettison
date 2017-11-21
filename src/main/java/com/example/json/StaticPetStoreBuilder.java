package com.example.json;

public final class StaticPetStoreBuilder {

    public static PetStore build() {
        Cat cat = new Cat();
        cat.setBreed("Cheetoh");
        cat.setNom("Tom");
        cat.setColor("Black");

        Dog dog = new Dog();
        dog.setBreed("German Sheperd");
        dog.setNom("Mike");
        dog.setFood("bones");

        PetStore store = new PetStore();
        store.getPets().add(cat);
        store.getPets().add(dog);

        return store;

    }

}
