import * as $ from 'jquery';

declare global {
  interface JQuery {
    owlCarousel(options?: any): JQuery;
  }
}
