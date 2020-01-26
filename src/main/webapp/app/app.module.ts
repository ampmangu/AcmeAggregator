import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AcmeAggregatorSharedModule } from 'app/shared/shared.module';
import { AcmeAggregatorCoreModule } from 'app/core/core.module';
import { AcmeAggregatorAppRoutingModule } from './app-routing.module';
import { AcmeAggregatorHomeModule } from './home/home.module';
import { AcmeAggregatorEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AcmeAggregatorSharedModule,
    AcmeAggregatorCoreModule,
    AcmeAggregatorHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AcmeAggregatorEntityModule,
    AcmeAggregatorAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class AcmeAggregatorAppModule {}
