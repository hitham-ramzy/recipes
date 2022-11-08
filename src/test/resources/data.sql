--SEED DATA FOR INTEGRATION TEST


-- INGREDIENTS
insert into ingredient (name) values ('Sugar');
insert into ingredient (name) values ('Oil');
insert into ingredient (name) values ('FLour');
insert into ingredient (name) values ('Salt');
insert into ingredient (name) values ('Eggs');


-- RECIPE_TYPES
insert into recipe_type (name) values ('VEGETERIAN');
insert into recipe_type (name) values ('NON VEGETERIAN');

-- RECIPES
insert into recipe (name, instructions, number_of_servings, type_id) values ('Bread', 'Add the Flour to oil and see the magic', 40, 1);

-- RECIPE_INGREDIENTS
insert into recipe_ingredient (recipe_id, ingredient_id, amount, measurement_unit) values (1, 2, 10, 1);
insert into recipe_ingredient (recipe_id, ingredient_id, amount, measurement_unit) values (1, 3, 0.5, 2);