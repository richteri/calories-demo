import { MealListComponent } from './meal-list.component';
import { Meal } from '../../../domain/meal';

describe('MealListComponent', () => {
  let component: MealListComponent;
  const meals: Meal[] = [
    {id: 1, date: '2018-01-01', time: '12:00:00', calories: 1000, description: 'Lunch'},
    {id: 2, date: '2018-01-01', time: '18:00:00', calories: 100, description: 'Dinner'},
    {id: 3, date: '2018-01-01', time: null, calories: 10, description: 'Breakfast'},
    {id: 4, date: null, time: '12:00:00', calories: 1000, description: 'Lunch'},
    {id: 5, date: null, time: '18:00:00', calories: 100, description: 'Dinner'},
    {id: 6, date: null, time: null, calories: 10, description: 'Breakfast'}
  ];

  beforeEach((done: any) => {
    component = new MealListComponent();
    done();
  });

  it('should sort meals', () => {
    component.meals = meals;
    expect(component.meals.map(meal => meal.id)).toEqual([6, 4, 5, 3, 1, 2]);
  });

  beforeEach((done: any) => {
    component = new MealListComponent();
    done();
  });

  it('should return correct group indices', () => {
    component.meals = meals;
    expect(component.group(null).index).toBe(0);
    expect(component.group('2018-01-01').index).toBe(3);
  });

  it('should return correct group sizes', () => {
    component.meals = meals;
    expect(component.group(null).size).toBe(3);
    expect(component.group('2018-01-01').size).toBe(3);
  });

  it('should return correct sum of calories', () => {
    component.meals = meals;
    expect(component.group(null).calories).toBe(1110);
    expect(component.group('2018-01-01').calories).toBe(1110);
  });

});
