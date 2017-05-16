import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MetricsSharedModule } from '../shared';

import {
    Register,
    Activate,
    Password,
    PasswordResetInit,
    PasswordResetFinish,
    SessionsService,
    SessionsComponent,
    PasswordStrengthBarComponent,
    RegisterComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    SocialRegisterComponent,
    accountState
} from './';

@NgModule({
    imports: [
        MetricsSharedModule,
        RouterModule.forRoot(accountState, { useHash: true })
    ],
    declarations: [
        SocialRegisterComponent,
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SessionsComponent,
        SettingsComponent
    ],
    providers: [
        SessionsService,
        Register,
        Activate,
        Password,
        PasswordResetInit,
        PasswordResetFinish
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetricsAccountModule {}
