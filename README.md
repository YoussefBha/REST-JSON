# Android app for Weather in the city of paris using REST/JSON API (openweathermap)

This is a weather android application for the city of paris.
The application includes 2 screens:
* A main screen with the 5-day weather forecast of Paris in the form of a list.
* A "detail" view.

## JSONParse :
JSON is very light weight, structured, easy to parse and much human readable. JSON is best alternative to XML when your android app needs to interchange data with your server.

As we are getting the JSON by making HTTP call, I am adding a **Async** class JSONParse to make http calls on background thread. Add the following method in your main activity class

In **doInBackground()** method, makeServiceCall() is called to get the json from url. Once the json is fetched, it is parsed and each contact is added to array list.

In **onPostExecute()** method the progress dialog is dismissed and the array list data is displayed in list view using an adapter.

    private class JSONParse extends AsyncTask<String, Void, String> {

        public JSONParse() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String str = "UNDEFINED";

            //Connexion test
            if(Utility.isNetworkAvailable(FiveDaysAvtivity.this)){

            try {
                //Connexion to service through service
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                //Getting JSON objects and array
           
                JSONObject jsonobject = new JSONObject(builder.toString());
                JSONArray jsonarray = jsonobject.getJSONArray("list");
                
                /* .....
                    ...
                    ....*/

            }
            // Failed
            catch (IOException | JSONException e) {
                e.printStackTrace();
                 str = "failed";
                
            }

            
            return str.trim();
        }

        //Post getting data function
        @Override
        protected void onPostExecute(String temp) {

           
        }
    }
