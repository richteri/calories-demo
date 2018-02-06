import { Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/observable/throw'
import 'rxjs/add/operator/catch';
import { MessageService } from 'primeng/components/common/messageservice';
import { AuthenticationService } from './authentication.service';

/**
 * HTTP Interceptor that sets Spring Security specific headers
 */
@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
  constructor(private messageService: MessageService,
              private authenticationService: AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.log("intercepted request ... ");

    // Clone the request to add the new header
    const clone = request.clone({ headers: request.headers.set('X-Requested-With', 'XMLHttpRequest') });

    console.log("Sending request with new header now ...");

    // Send the cloned request
    return next.handle(clone)
      .catch((error, caught) => {
        this.messageService.add({severity: 'error', summary: 'API Error', detail: error.message});
        console.log(error);
        return Observable.throw(error);
      }) as any;
  }
}
