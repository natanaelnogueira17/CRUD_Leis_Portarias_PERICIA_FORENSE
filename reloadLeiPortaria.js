(() => {

	if (window.localStorage) {

		if (!localStorage.getItem('reload')) {
			localStorage['reload'] = true;
			window.location.reload();

		} else {
			localStorage.removeItem('reload');
		}
	}
}
)();


		
		
		