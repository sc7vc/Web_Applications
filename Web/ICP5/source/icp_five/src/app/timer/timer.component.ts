import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.css']
})
export class TimerComponent implements OnInit {

  timer_time = '';
  Destination = '2022-04-27T20:49:48';
  sec = '00';
  
  // this is to update the timer
  private readonly update = () => {
    let time_period = Date.parse(this.Destination) - Date.now();
    if (time_period < 0) {
      clearInterval(this.timer);
      this.timer_time = 'finished';
      return;
    }

    // calculates date and time
    const days = Math.floor(time_period / (1000 * 60 * 60 * 24));
    time_period -= days * 1000 * 60 * 60 * 24;

    const hours = Math.floor(time_period / (1000 * 60 * 60));
    time_period -= hours * 1000 * 60 * 60;

    const minutes = Math.floor(time_period / (1000 * 60));
    time_period -= minutes * 1000 * 60;

    this.sec = pretty(Math.floor(time_period / 1000));

    function pretty(value: number) {
      return value.toString().padStart(2, '0');
    }
    // Timer to display
    this.timer_time = ` Days: ${days}, Hours: ${pretty(hours)}, Minutes: ${pretty(minutes)} `;
  }

  private timer = setInterval(this.update, 1000);

  ngOnInit() {
    this.update();
  }

}
