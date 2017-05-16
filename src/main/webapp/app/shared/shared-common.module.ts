import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AlertService } from 'ng-jhipster';
import {
    MetricsSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

export function alertServiceProvider(sanitizer: Sanitizer) {
    // set below to true to make alerts look like toast
    const isToast = false;
    return new AlertService(sanitizer, isToast);
}

@NgModule({
    imports: [
        MetricsSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer]
        },
        Title
    ],
    exports: [
        MetricsSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class MetricsSharedCommonModule {}
