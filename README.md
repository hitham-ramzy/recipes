# Recipes Application
A task of searching for Recipes and its Ingredients 

### The used Entities

- The Recipe Entity : A model for the Recipe:

    - name
    - type (VEGETARIAN, NON_VEGETARIAN, VEGAN)
    - instructions
    - numberOfServings
    - ingredients


- The Ingredients Entity : A model for the Ingredients:

    - unit : the unit used for that ingredient


- The Recipe Ingredients Entity : the entity links between the Recipe and its Ingredients as the relationship between the Recipe to Ingredients is many-to-many relationship

  - recipe
  - amount