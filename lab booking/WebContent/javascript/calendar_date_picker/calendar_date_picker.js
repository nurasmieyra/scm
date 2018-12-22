/******
* You may use and/or modify this script as long as you:
* 1. Keep my name & webpage mentioned
* 2. Don't use it for commercial purposes
*
* If you want to use this script without complying to the rules above, please contact me first at: marty@excudo.net
*
* Please be aware that this is only the core source-code of a full script and that it may not work (for you) without the rest.
* The full script can be downloaded from my website. A working example is provided there as well.
* 
* Author: Martijn Korse
* Website: http://devshed.excudo.net
*
* Date: 2008-08-11 17:55:53
***/

/**
 * Calendar Date Picker constructor
 * 
 * This is how you can create an object:
 * 
 * var cdp = new CalendarDatePicker({
 *	debug :			true,
 *	weekStartsAt :		1,
 *	excludeDays :		[6,0,1],
 *	excludeDates :		['20081225','20081226'],
 *	minimumFutureDate :	[7,true],
 *	formatDate :		'%d-%m-%y'
 *	});
 *
 * if you are unfamiliar with this syntax, you could also try this notation:
 *
 * var props = {};
 * props.debug			= true;
 * props.weekStartsAt		= 1;
 * props.excludeDays		= [6,0,1];
 * props.excludeDates		= ['20081225','20081226'];
 * props.minimumFutureDate	= [7,true];
 * props.formatDate		= '%d-%m-%y';
 * var cdp = new CalendarDatePicker(props);
 *
 * which does exactly the same.
 *
 * These are the properties that can be set:
 * debug		if true, error messages will be shown (in the form of alerts)
 * excludeDays		days (of the week) that are not selectable (0-6; 0=sunday, 6=saturday)
 * excludeDates		array of dates that are not selectable. format: yyyymmdd
 * minimumDate		dates before this date are not selectable. format: yyyymmdd
 * maximumDate		dates after this date are not selectable. format: yyyymmdd
 * weekStartsAt		indicates on which day a week in the calendar must start (0-6; 0=sunday, 6=saturday)
 * minimumFutureDate	same as minimum date but defined as an offset on the current date.
 *			must be set as an array of which the second argument indicates whether excluded days should be considered as well
 * startYear		first selectable year in the select-box of the calendar
 * endYear		last selectable year in the select-box of the calendar
 * 
 * For a more extensive overview and explanation of the properties, see:
 * http://devshed.excudo.net/scripts/javascript/example/calendar_date_picker_help.php
 *
 * @param array props
 * @return CalendarDatePicker
 */
function CalendarDatePicker (props)
{
	// defining some properties
	// these can not change
	this.NAME		= 'Calendar Date Picker';
	this.VERSION		= '2.11';
	// private properties
	this._monthMaxDays	= [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	this._monthMaxDaysLeap	= [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	this._hideSelectTags	= [];
	this._targetEl		= false;

	// defining more properties and setting their defaults
	// these can be customized by the user
	this.debug		= false;
	this.excludeDays	= [];
	this.excludeDates	= [];
	this.minimumDate	= false;
	this.maximumDate	= false;
	this.weekStartsAt	= 0;	// 0=sunday, 1=monday, 2=tuesday, 3=wednesday, 4=thursday, 5=friday, 6=saturday
	this.formatDate		= '%d-%m-%y';
	this.futureDays		= 0;
	this.futureWorkingDays	= 0;

	this.startYear		= CalendarDatePicker.prototype.getRealYear(new Date);
	this.endYear		= this.startYear + 10;

	if (typeof(props) != 'undefined')
	{
		// setting custom values that were passed by the user.
		if (typeof(props.debug) != 'undefined')
		{
			if (props.debug == false || props.debug == true)
				this.debug = props.debug;
			else
				alert('you didn\'t set the debug flag correctly');
		}
		if (typeof(props.startYear) != 'undefined')
		{
			var startYear = parseInt(props.startYear);
			if (startYear <= 0 || isNaN(startYear))
				this.debug ? alert('you\'ve tried to set an illegal year ('+props.startYear+') as minimum year') : '';
			else
				this.startYear = props.startYear;
		}
		if (typeof(props.endYear) != 'undefined')
		{
			var endYear = parseInt(props.endYear);
			if (endYear <= 0 || isNaN(endYear))
				this.debug ? alert('you\'ve tried to set an illegal year ('+props.endYear+') as maximum year') : '';
			else if (props.endYear < this.startYear)
				this.debug ? alert('you\'ve tried to set a value as maximum year ('+props.endYear+') that is before the start year ('+this.startYear+')') : '';
			else
				this.endYear = props.endYear;
		}
		if (typeof(props.excludeDays) != 'undefined')
		{
			for (var i in props.excludeDays)
			{
				if (props.excludeDays[i] < 0 || props.excludeDays[i] > 6)
				{
					this.debug ? alert(props.excludeDays[i]+' is not a valid day to exclude. it has to be between 0 and 6') : '';
					props.excludeDays = this.excludeDays;
					break;
				}
			}
			this.excludeDays = props.excludeDays;
		}
		if (typeof(props.excludeDates) != 'undefined')
		{
			for (var i in props.excludeDates)
			{
				if (!props.excludeDates[i].match(/^\d{8}$/))
				{
					this.debug ? alert(props.excludeDates[i]+' is not a valid date to exclude. it has to be of the format yyyymmdd') : '';
					props.excludeDates = this.excludeDates;
					break;
				}
			}
			this.excludeDates = props.excludeDates;
		}
		if (typeof(props.formatDate) != 'undefined')
		{
			var f = props.formatDate;
			if (!f.match(/%d/) || !f.match(/%m/) || !f.match(/%y/))
			{
				this.debug ? alert(props.formatDate+' is not a valid format. it needs to contains %d, %m & %y') : '';
			}
			else
			{
				this.formatDate = props.formatDate;
			}
		}

		// the minimum date the calendar accepts as pickable
		// first we check if a future date has been set (current timestamp + offset); if not, we check if a specific date has been set
		if (typeof(props.minimumFutureDate) != 'undefined')
		{
			if (typeof(props.minimumFutureDate) == 'object')
				this.setFutureDate(props.minimumFutureDate[0], props.minimumFutureDate[1]);
			else
				this.setFutureDate(props.minimumFutureDate);
		}
		else if (typeof(props.minimumDate) != 'undefined')
			this.minimumDate = props.minimumDate;
		//minimum date is set, now the maximum date the calendar accepts as pickable
		if (typeof(props.maximumDate) != 'undefined')
			this.maximumDate = props.maximumDate;
		// this indicates at which day the week in the calendar starts, ranging from 0 (sunday) to 6 (saturday)
		if (typeof(props.weekStartsAt) != 'undefined')
		{
			if (props.weekStartsAt < 0 || props.weekStartsAt > 6)
				this.debug ? alert(props.weekStartsAt+' is not a valid starting point of the week. it has to be between 0 (sunday) and 6 (saturday)') : '';
			else
				this.weekStartsAt = props.weekStartsAt;
		}

		// based on the set properties we're gonna set some initial values using some methods
		this.setYears(this.startYear, this.endYear);
	}
}
/**
 * returns the year in format YYYY
 * @param Date dateObj
 * @return int
 */
CalendarDatePicker.prototype.getRealYear = function (dateObj)
{
	return (dateObj.getYear() % 100) + (((dateObj.getYear() % 100) < 39) ? 2000 : 1900);
}
/**
 * returns the amount of days a certain month has, thereby taking leap-years into account
 * @param int month the month of which we want to know the amount of days. Note: january = 0
 * @param int year the year in which the calendar is set
 * @return int
 */
CalendarDatePicker.prototype.getDaysPerMonth = function (month, year)
{
	/* 
	Check for leap year ..
	1.Years evenly divisible by four are normally leap years, except for... 
	2.Years also evenly divisible by 100 are not leap years, except for... 
	3.Years also evenly divisible by 400 are leap years. 
	*/
	if ((year % 4) == 0)
	{
		if ((year % 100) == 0 && (year % 400) != 0)
			return this._monthMaxDays[month];
	
		return this._monthMaxDaysLeap[month];
	}
	else
		return this._monthMaxDays[month];
}
/**
 * Creates the calender.
 * It creates an array of dom-elements: all the table-rows which contain the days of the current year+month
 * @param int year
 * @param int month
 * @param int day
 * @return array
 */
CalendarDatePicker.prototype.createCalendar = function (year, month, day)
{
	 // current Date
	var curDate = new Date();
	var curDay = curDate.getDate();
	var curMonth = curDate.getMonth();
	var curYear = this.getRealYear(curDate)

	 // if a date already exists, we calculate some values here
	if (!year)
	{
		var year = curYear;
		var month = curMonth;
	}

	var yearFound = 0;
	for (var i=0; i<document.getElementById('selectYear').options.length; i++)
	{
		if (document.getElementById('selectYear').options[i].value == year)
		{
			document.getElementById('selectYear').selectedIndex = i;
			yearFound = true;
			break;
		}
	}
	if (!yearFound)
	{
		document.getElementById('selectYear').selectedIndex = 0;
		year = document.getElementById('selectYear').options[0].value;		
	}
	if (month < 0 || month > 11)
	{
		month = curMonth;
	}
	document.getElementById('selectMonth').selectedIndex = month;

	 // first day of the month is a ....
	var firstDayOfMonthObj = new Date(year, month, 1);
	// compensating in case the first day is not a sunday
	var firstDayOfMonth = firstDayOfMonthObj.getDay() + (7 - this.weekStartsAt);
	if (firstDayOfMonth > 7)
		firstDayOfMonth -= 7;

	var firstRow	= true;
	var x	= 0;
	var d	= 0;
	var trs = []
	var ti = 0;
	while (d <= this.getDaysPerMonth(month, year))
	{
		if (firstRow)
		{
			// the first row. we have to find out at which position we start with the first day
			trs[ti] = document.createElement("TR");
			if (firstDayOfMonth > 0)
			{
				while (x < firstDayOfMonth)
				{
					trs[ti].appendChild(document.createElement("TD"));
					x++;
				}
			}
			firstRow = false;
			var d = 1;
		}
		// end of the row, we have to start a new one
		if (x % 7 == 0)
		{
			ti++;
			trs[ti] = document.createElement("TR");
		}
		// this is the day the user has in the input-element
		if (day && d == day)
		{
			var setID = 'calendarChoosenDay';
			var styleClass = 'choosenDay';
			var setTitle = 'this day is currently selected';
		}
		// today
		else if (d == curDay && month == curMonth && year == curYear)
		{
			var setID = 'calendarToDay';
			var styleClass = 'toDay';
			var setTitle = 'this day today';
		}
		// just an ordinary day
		else
		{
			var setID = false;
			var styleClass = 'normalDay';
			var setTitle = false;
		}
		var td = document.createElement("TD");
		td.className = styleClass;
		if (setID)
		{
			td.id = setID;
		}
		if (setTitle)
		{
			td.title = setTitle;
		}

		// doing some calculation to see if this date is pickable
		var dObj = new Date(year, month, d);
		var fullDate = year+''+this.addZero((month+1))+''+this.addZero(d);
		var minDateOk = !this.minimumDate || parseInt(fullDate) >= this.minimumDate;
		var maxDateOk = !this.maximumDate || parseInt(fullDate) <= this.maximumDate;
		if (minDateOk && maxDateOk && !this.inArray(dObj.getDay(), this.excludeDays) && !this.inArray(fullDate, this.excludeDates))
		{
			td.onmouseover = new Function('CalendarDatePicker.prototype.highLiteDay(this)');
			td.onmouseout = new Function('CalendarDatePicker.prototype.deHighLiteDay(this)');
			var self = this;
			var method = 'pickDate';
			// date is pickable, but only if there is a target element in which to show the date-string
			if (this._targetEl)
			{
				td.onclick = this.pickDate(self, year, month, d);
			}	
			else
			{
				td.style.cursor = 'default';
			}
		}
		else
		{
			// date not pickable. 
			td.style.cursor = 'default';
			td.className += ' excludedDay';
		}
		td.appendChild(document.createTextNode(d));
		trs[ti].appendChild(td);
		x++;
		d++;
	}
	return trs;
}
/**
 * shows the calendar on the screen
 * @param Obj elPos the HTML-element that represents the calender. we want to know it, because that's where we want to place the real calendar
 * @param Obj tgtEl the HTML-element in which we have to show the date when it gets clicked (picked)
 * @return void
 */
CalendarDatePicker.prototype.showCalendar = function (el, tgtEl)
{
	// we set the target element to false by default and will later overwrite it when it appears it really exists
	this._targetEl = false;

	// setting reference to this object
	var self = this;

	// setting the years for this calendar
	this.setYearsInSelect();

	// registering onclick for the close button
	document.getElementById('closeCalendarLink').onclick = function ()
	{
		self.closeCalendar();
	}

	// registering the onchange for the selection of the month
	document.getElementById('selectMonth').onchange = function()
	{
		var selectedYear = document.getElementById('selectYear').value;
		self.showCalendarBody(self.createCalendar(selectedYear, document.getElementById('selectMonth').selectedIndex, false));
	}
	// registering the onchange for the select of the year
	document.getElementById('selectYear').onchange = function()
	{
		var selectedYear = document.getElementById('selectYear').value;
		self.showCalendarBody(self.createCalendar(selectedYear, document.getElementById('selectMonth').selectedIndex, false));
	}

	if (document.getElementById(tgtEl))
	{
		this._targetEl = document.getElementById(tgtEl);
	}
	else
	{
		if (document.forms[0].elements[tgtEl])
		{
			this._targetEl = document.forms[0].elements[tgtEl];
		}
		else
		{
			this.debug ? alert('Target field could not be found\nCalendar won\'t open therefor') : '';
		}
	}
	var calTable = document.getElementById('calendarTable');

	// determining where to place the calendar
	var positions = getElementPosition(el);
	calTable.style.left = positions[0]+'px';
	calTable.style.top = positions[1]+'px';
	// coordinates are known, we show it.
	calTable.style.display='block';

	// let's see if the value of the target element contains a valid date.
	// if so, we're gonna use that month and year as the one we show
	if (tgtEl != false && this._targetEl.value.length > 0)
	{
		var inputDate = this.getDateFromFormattedDate(this._targetEl.value);
	}
	else
	{
		var inputDate = '';
	}
	var matchDate = new RegExp('^([0-9]{2})-([0-9]{2})-([0-9]{4})$');
	var m = matchDate.exec(inputDate);
	if (m == null)
	{
		trs = this.createCalendar(false, false, false);
		this.showCalendarBody(trs);
	}
	else
	{
		if (m[1].substr(0, 1) == 0)
			m[1] = m[1].substr(1, 1);
		if (m[2].substr(0, 1) == 0)
			m[2] = m[2].substr(1, 1);
		m[2] = m[2] - 1;
		trs = this.createCalendar(m[3], m[2], m[1]);
		this.showCalendarBody(trs);
	}
	// hiding any select-fields that are localted under the calendar
	this.hideSelect(document.body, 1);
}
/**
 * displaying the body of the calendar
 * @param array trs
 * @see createCalendar()
 * @return void
 */
CalendarDatePicker.prototype.showCalendarBody = function (trs)
{
	var calTBody = document.getElementById('calendar');
	while (calTBody.childNodes[0])
	{
		calTBody.removeChild(calTBody.childNodes[0]);
	}
	for (var i in trs)
	{
		calTBody.appendChild(trs[i]);
	}
}
/**
 * public function to set the starting and ending year of the calendar
 * @param sy int startyear: the first available year in the select-box of the calendar
 * @param ey int endyear: the last available year in the select-box of the calendar
 * @return void
 */
CalendarDatePicker.prototype.setYears = function (sy, ey)
{
	this.startYear = sy;
	this.endYear = ey;
}
/**
 * fills the year-selectbox of the calendar with the available years
 * @see setYears()
 * @see showCalendar()
 * @return void
 */
CalendarDatePicker.prototype.setYearsInSelect = function ()
{
	 // current Date
	var curDate = new Date();
	var curYear = this.getRealYear(curDate);
	if (this.startYear)
		startYear = curYear;
	if (this.endYear)
		endYear = curYear;
	document.getElementById('selectYear').options.length = 0;
	var j = 0;
	for (y=this.endYear; y>=this.startYear; y--)
	{
		document.getElementById('selectYear')[j++] = new Option(y, y);
	}
}
/**
 * This function is in charge of hiding any select-box that would be locataed directly underneath the calendar in the moment we show it.
 * This is necessary because Internet Exploder will mess up the calendar otherwise. (In a worst case, it would render the close button inoperable)
 * @param object el		refernce to a DOM-element. When the function is called for the first time, this will be the body-tag
 * @param int superTotal	keeps track of how many times we recurse. it's a failsafe for infinite loops, because when it reaches a certain maximum it quits the function.
 * @return void
 */
CalendarDatePicker.prototype.hideSelect = function (el, superTotal)
{
	if (superTotal >= 100)
	{
		return;
	}

	var totalChilds = el.childNodes.length;
	for (var c=0; c<totalChilds; c++)
	{
		var thisTag = el.childNodes[c];
		if (thisTag.tagName == 'SELECT')
		{
			if (thisTag.id != 'selectMonth' && thisTag.id != 'selectYear')
			{
				var calendarEl = document.getElementById('calendarTable');
				var positions  = getElementPosition(thisTag);
				var thisLeft	= positions[0];
				var thisRight	= positions[0] + thisTag.offsetWidth;
				var thisTop	= positions[1];
				var thisBottom	= positions[1] + thisTag.offsetHeight;
				var calLeft	= calendarEl.offsetLeft;
				var calRight	= calendarEl.offsetLeft + calendarEl.offsetWidth;
				var calTop	= calendarEl.offsetTop;
				var calBottom	= calendarEl.offsetTop + calendarEl.offsetHeight;

				if (
					(
						/* seeing if it overlaps horizontally */
						(thisLeft >= calLeft && thisLeft <= calRight)
							||
						(thisRight <= calRight && thisRight >= calLeft)
							||
						(thisLeft <= calLeft && thisRight >= calRight)
					)
						&&
					(
						/* seeing if it overlaps vertically */
						(thisTop >= calTop && thisTop <= calBottom)
							||
						(thisBottom <= calBottom && thisBottom >= calTop)
							||
						(thisTop <= calTop && thisBottom >= calBottom)
					)
				)
				{
					this._hideSelectTags[this._hideSelectTags.length] = thisTag;
					thisTag.style.visibility = 'hidden';
				}
			}

		}
		else if(thisTag.childNodes.length > 0)
		{
			this.hideSelect(thisTag, (superTotal+1));
		}
	}
}
/**
 * Checks whether the passed needle (any value) can be found in the haystack (an array)
 * @param mixed needle		the value of which we want to know whether it exists in a certain array
 * @param array haystack	the array in which the needle may (not) be
 * @return boolean		true when the needle was found
 */
CalendarDatePicker.prototype.inArray = function (needle, haystack)
{
	for (var i in haystack)
	{
		if (needle == haystack[i])
			return true;
	}
	return false;
}
/**
 * Adds a zero when the passed value is below 10
 * Bit of a primitive function in its current form, but it serves its purpose in this class
 * @param integer/string x (if it's a string it should be a string holding a number)
 * @return string
 */
CalendarDatePicker.prototype.addZero = function (x)
{
	if (parseInt(x) < 10)
		x = '0'+x;
	return x;
}
/**
 * Closes the calendar (hides it)
 * This should happen when:
 * - the user clicks the X on the top-right
 * - the user selects a date from the calendar
 * @return void
 */
CalendarDatePicker.prototype.closeCalendar = function ()
{
	for (var i=0; i<this._hideSelectTags.length; i++)
	{
		this._hideSelectTags[i].style.visibility = 'visible';
	}
	this._hideSelectTags.length = 0;
	document.getElementById('calendarTable').style.display='none';
}
/**
 * Highlights a day on the calendar when the mouse-pointer hovers over it
 * It does so by switching stylesheet-classes
 * (so, if there is nothing defined in the stylesheet, you won't see any effect
 * @param obj el reference to the dom-object that is the cell that is holding the value of the day
 * @see deHighLiteDay()
 */
CalendarDatePicker.prototype.highLiteDay = function (el)
{
	el.className = 'hlDay';
}
/**
 * Same as highLiteDay(), but returns the cell to it's original value again
 * @param obj el reference to the dom-object that is the cell that is holding the value of the day
 * @see highLiteDay()
 */
CalendarDatePicker.prototype.deHighLiteDay = function(el)
{
	if (el.id == 'calendarToDay')
		el.className = 'toDay';
	else if (el.id == 'calendarChoosenDay')
		el.className = 'choosenDay';
	else
		el.className = 'normalDay';
}
/**
 * Selects a date from the calendar and sets it as the value of the target element
 * @param CalendarDatePicker obj	The current CalendarDatePicker object
 * @param integer year			The year that has been selected
 * @param integer month			The month that has been selected
 * @param integer day			The day that has been selected
 * @return void
 */
CalendarDatePicker.prototype.pickDate = function (obj, year, month, day)
{
	return function () {
		month++;
		day	= day < 10 ? '0'+day : day;
		month	= month < 10 ? '0'+month : month;
		if (!obj._targetEl)
		{
			this.debug ? alert('target for date is not set yet') : '';
		}
		else
		{
			var outputDate = obj.formatDate.replace(/%d/, day);
			outputDate = outputDate.replace(/%m/, month);
			outputDate = outputDate.replace(/%y/, year);
			obj._targetEl.value= outputDate;
			obj.closeCalendar();
		}
	}
}

/**
 * Given a certain offset, it returns that amount of normal days that is required to reach that offset
 * offset is relative to current day
 * If there is not any date excluded, the returned value will be the same as the offset.
 * If all days have to be counted, the returned value can still differ from the offset, because an exluded day will never be allowed as the result
 * @param integer days		The offset
 * @param boolean workingDays	Determines whether the offset is in normal days or only days that are not excluded
 *				The term 'workingDays' has been used as a symbol for this
 * @return integer
 */
CalendarDatePicker.prototype.getDayByOffset = function(days, workingDays)
{
   	var curDate = new Date();
    	curDate.setHours(0);
    	curDate.setMinutes(0);
    	curDate.setSeconds(0);
    	curDate.setMilliseconds(0);
    	var passedWorkingDays = 0;
    	var passedDays = 0;
    	var unixToday = curDate.getTime();
    	var loopDate = new Date();
    	while (passedWorkingDays < days)
    	{
    		passedDays += 1;
    		var minUnixTime = unixToday + (1000 * 60 * 60 * 24 * passedDays);
    		loopDate.setTime(minUnixTime);
	
    		if (!workingDays || !this.dateIsExcluded(loopDate))
        	{
    			passedWorkingDays += 1;
    		}
    	}
	// final check. this could be true when we're not counting working days but normal days
	// still, we don't want to return a date that is excluded
	var addDays = 0;
	if (this.dateIsExcluded(loopDate))
	{
		while (this.dateIsExcluded(loopDate))
		{
	    		addDays += 1;
    			var minUnixTime = unixToday + (1000 * 60 * 60 * 24 * addDays);
    			loopDate.setTime(minUnixTime);
		}
	}
    	return (passedDays + addDays);
}
/**
 * Determines whether a date is excluded (by means of the excludedDays & excludedDates properties).
 * @param Date dateObj (date-object (with a certain date set))
 * @return boolean
 */
CalendarDatePicker.prototype.dateIsExcluded = function(dateObj)
{
	if (this.inArray(dateObj.getDay(), this.excludeDays) || this.inArray(this.getDate(dateObj), this.excludeDates))
		return true;
	else
		return false;
}
/**
 * Calculates a date in the future by adding 'offset' days to the current date
 * Optionally, it can take into account days that are not selectable in this calculation.
 * @param integer offset	
 * @param boolean countAllDays	If true, unselectable days also count. If false, they are skipped.
 * @return string (date in the format yyyymmdd)
 */
CalendarDatePicker.prototype.getFutureDate = function (offset, countAllDays)
{
	if (typeof(countAllDays) == 'undefined')
		countAllDays = true;
	var days = this.getDayByOffset(offset, !countAllDays);
	var curDate = new Date();
	var minUnixTime = curDate.getTime() + (1000 * 60 * 60 * 24 * days);
	curDate.setTime(minUnixTime);
	return this.getDate(curDate);
}
/**
 * Same as above, but sets it as a property of the instantiated object
 * The property that is set is minimumDate
 * @return void
 */
CalendarDatePicker.prototype.setFutureDate = function (offset, countAllDays)
{
	if (typeof(countAllDays) == 'undefined')
		countAllDays = true;
	this.minimumDate = this.getFutureDate(offset, countAllDays);
}
/**
 * given a date-object, it returns that date in the format yyyymmdd
 * @param Date dateObj
 * @return string
 */
CalendarDatePicker.prototype.getDate = function (dateObj)
{
	return this.getRealYear(dateObj)+''+this.addZero(dateObj.getMonth()+1)+''+this.addZero(dateObj.getDate());
}
/**
 * given the date-string of the target-element (an input field of a form), this returns that value in the format dd-mm-YYYY
 * (this might seem like an odd choice, but since the old code was already expecting it and it is only used internal, it's as good a format as any other)
 * it does so by extracting the day, month & year from that string, based on the format that has been set by the user
 * @param string dateStr
 * @return string
 */
CalendarDatePicker.prototype.getDateFromFormattedDate = function (dateStr)
{
	if (this.formatDate.indexOf('%d', 0) < this.formatDate.indexOf('%m', 0))
	{
		if (this.formatDate.indexOf('%y', 0) < this.formatDate.indexOf('%d', 0))
			var dmy = ['y','d','m'];
		else if (this.formatDate.indexOf('%y', 0) > this.formatDate.indexOf('%m', 0))
			var dmy = ['d','m','y'];
		else
			var dmy = ['d','y','m'];
	}
	else
	{
		if (this.formatDate.indexOf('%y', 0) < this.formatDate.indexOf('%m', 0))
			var dmy = ['y','m','d'];
		else if (this.formatDate.indexOf('%y', 0) > this.formatDate.indexOf('%d', 0))
			var dmy = ['m','d','y'];
		else
			var dmy = ['m','y','d'];
	}
	var offset = 0;
	seperationLength = (dateStr.length - 8) / 2;
	for (var i in dmy)
	{
		switch (dmy[i])
		{
			case 'd' :
				var day = dateStr.substr(offset, 2);
				offset += (2 + seperationLength);
				break;
			case 'm' :
				var month = dateStr.substr(offset, 2);
				offset += (2 + seperationLength);
				break;
			case 'y' :
				var year = dateStr.substr(offset, 4);
				offset += (4 + seperationLength);
				break;
		}
	}
	return day+'-'+month+'-'+year;
}

/******
* The following two functions are maintained independantly from the calendar-date-picker script at:
* http://devshed.excudo.net/scripts/javascript/source/get+element+position
* You are encouraged to place them in a seperate file; although this script will always contain the latest code with every update, it will not
* get updated because of a code change in those functions.
* Moreover, even though it works, i'm not overly happy with this code and any discussions should take place there.
***/
function calcPositionCorrection(el)
{
	var curX = 0;
	var curY = 0;
	do {
		curX += el.scrollLeft;
		curY += el.scrollTop;
		if (el.nodeName == 'BODY')
			break;
	} while (el = el.parentNode);
	return [curX, curY];
}
function getElementPosition(el)
{
	var curLeft = 0;
	var curTop = 0;
	var corrections = calcPositionCorrection(el);

	if (el.offsetParent)
	{
		do {
			curLeft += el.offsetLeft;
			curTop += el.offsetTop;
		} while (el = el.offsetParent);
		return [ (curLeft - corrections[0]), (curTop - corrections[1]) ];
	}
}