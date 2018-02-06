import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../domain/user';
import { Observable } from 'rxjs/Observable';
import { Meal } from '../domain/meal';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  findAll(): Observable<User[]> {
    return this.http.get<User[]>('/api/users');
  }

  findOne(id: number): Observable<User> {
    return this.http.get<User>('/api/users/' + id);
  }

  create(user: User): Observable<User> {
    return this.http.post<User>('/api/users', user);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>('/api/users/' + user.id, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>('/api/users/' + id);
  }

  findMeals(user: User): Observable<Meal[]> {
    return this.http.get<Meal[]>(`/api/users/${user.id}/meals`);
  }

  deleteMeal(user: User, meal: Meal): Observable<void> {
    return this.http.delete<void>(`/api/users/${user.id}/meals/${meal.id}`);
  }

  updateMeal(user: User, meal: Meal): Observable<Meal> {
    return this.http.put<Meal>(`/api/users/${user.id}/meals/${meal.id}`, meal);
  }

  createMeal(user: User, meal: Meal): Observable<Meal> {
    return this.http.post<Meal>(`/api/users/${user.id}/meals`, meal);
  }
}
