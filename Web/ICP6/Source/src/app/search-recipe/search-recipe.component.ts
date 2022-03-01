import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})

export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  venue_details: any;
  recipe_details: any;
  recipeValue: any;
  placeValue: any;
  restaurants: any;
  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {
    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      const url_init = 'https://api.edamam.com/api/recipes/v2?type=public&limit=10&q=';
      const app_id = '5f536fb4';
      const app_key = '955bb0bbc1b668ee35f3b6f0b53710d4';
      const url = url_init + this.recipeValue + '&app_id=' + app_id + '&app_key=' + app_key ;
      this._http.get(url).subscribe((data_: any) => {
        this.recipe_details =  Object.keys(data_.hits).map(function (j) {
          const x = data_.hits[j].recipe;
          return {name : x.label, url : x.url, icon : x.image};
        }
      );
      });
    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      const url_init = 'https://api.foursquare.com/v3/places/search?client_id=';
      const client_id = '5SEF1P5JXBYHVX2XRU3SWXZZV2J0ICSCXL2TMYJXY4VCKEIY';
      const client_secret = 'NLRNOOO05HWGE0VZ1CBE3X1XNUNX2YRVO2YQK5VHRA2H2HHQ';
      // tslint:disable-next-line:max-line-length
      const url = url_init + client_id + '&client_secret=' + client_secret + '&v=20220202&limit=10&near=' + this.placeValue + '&radius=1000&query=' + this.recipeValue;
      
      this._http.get(url).subscribe((data: any) => {
        this.venue_details = Object.keys(data.response.groups[0].items).map(function (k) {
          const i = data.response.groups[0].items[k].venue;
          return {name: i.name, location : i.location, contact : i.contact};
        });
      });
    }
  }


  
}

