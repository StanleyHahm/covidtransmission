/**
*  NAME: Stanley Hahm
*  ID: A14609365
*  EMAIL: sthahm@ucsd.edu
*
*  In this code below, we are trying to keep track of Covid Transmission
*  of students on campus. We are using multiple methods on keeping track
*  of locations, infections, and the amount of infections per student.
*  One new concept we are utilizing for this PA are arrays. This is
*  utilized to list out and go through each of the students when we're
*  going through the methods.
*/

import java.io.File;
import java.util.Scanner;
import java.io.IOException;

/**
*  This class uses multiples methods. All of this is to check how
*  COVID transmitted on our campus. The populateArrays makes sure
*  our arrays are valid. The updateLocations updates the location
*  of students based on how much they moved by. The updateInfections
*  method checks if students at areas where COVID was exposed and
*  updates if they're effected or not. The countInfectionsByStudent
*  method counts which students transmitted COVID and how many
*  students they transmitted to. The findRNaught finds the average
*  numbers of people who will contract the disease. And findSuperSpreader
*  will detect who spread the disease the most first.
*/

public class InfectionTracking{

  /**
  *  Reads through the specific file and extracts the names, locations,
  *  movements, and any infectionsa and puts them into arrays. It then
  *  runs through the location to find the biggest location among the
  *  students.
  *
  *  @param PathtoFile name of the file being read
  *  @param names names of the students involved
  *  @param locations locations of each of the students
  *  @param movements where the students moved in relative to their location
  *  @param infections which students were infected at the time of their
  *  initial location
  *  @return maxLocation the greatest location + 1
  */
  public static int populateArrays(String PathToFile, String[] names,
  int[] locations, int[] movements, int[] infections) throws IOException {
    //has name of file and then access file
    File sourceFile = new File(PathToFile);
    Scanner input = new Scanner(sourceFile);
    input.useDelimiter("\\s*[,\\n]\\s*");
    //the max location amount in the file
    int maxLocation = 0;

    //if we can access the file, we scan through the file to find name,
    //locations, movements, and infecitons
    if(PathToFile != null){
      int count = 0;
      while(input.hasNext() && count < names.length){
        names[count] = input.next();
        locations[count] = input.nextInt();
        movements[count] = input.nextInt();
        infections[count] = input.nextInt();
        count++;
      }
      //if all arrays values are the same...
      if((names.length == locations.length)
        && (names.length == movements.length)
        && (names.length == infections.length)){
          //for each value in location, check if it is bigger than
          //max location - 1 ('-1' because 0 is 1st in array)
          for(int i = 0; i < locations.length; i++){
            if (locations[i] > maxLocation - 1){
              maxLocation = locations[i] + 1;
            }
          }
      }
      else{
        return -1;
      }
      //if the infection value is anything other than 0 or 1,
      //return -1
      for(int j = 0; j < names.length; j++){
        if((infections[j] != 0) && (infections[j] != 1 )){
          return -1;
        }
      }
    }
    else{
      return -1;
    }

  return maxLocation;
  }
  /**
  *  This method below updates the locations of the students. Based on
  *  their starting location and they movement, it'll change the location
  *  of the students and where they're at after that movement.
  *
  *  @param worldSize the value of how big the world is that the
  *  students are in
  *  @param locations locations of each of the students
  *  @param movements where the students moved in relative to their location
  *  @return nothing this is a void method
  */
  public static void updateLocations(int worldSize, int[] locations,
  int[] movements) {
    //checking if arrays are not null and if they're same length
    if((locations != null) && (movements != null) && (worldSize > 0)
      && (locations.length == movements.length)){
        //approval count is used to validify if the whole array passes
        int approval_count = 0;
        //if location has a value below 0 or out of worldSize then return
        //location as it was given
        for(int i = 0; i < locations.length; i++){
          if((locations[i] < 0) || (locations[i] >= worldSize)){
              break;
          }
          else{
            approval_count++;
          }
        }
        //if the approval count gets the value it's supposed to
        if(approval_count == locations.length){
          for(int j = 0; j < locations.length; j++){
            //if the new location after movement is negative, then find
            //the remainder to world size and then add it by worldSize
            //to wrap around 0 index
            if((locations[j] + movements[j]) < 0){
              locations[j] = ((locations[j] + movements[j]) % worldSize)
              + worldSize;
            }
            //if new location after movement is positive, then just find
            //the modulo of it to find the new location. This accounts for
            //wrapping around the max index.
            else{
              locations[j] = (locations[j] + movements[j]) % worldSize;
            }
          }
        }
    }
  }

  /**
  *  This method below updates the if students have been infected by
  *  disease based on if the COVID carrying students were at a location
  *  that other students are now in or exposed to.
  *
  *  @param worldSize the value of how big the world is that the
  *  students are in
  *  @param infections which respective element has an infection or not
  *  @param locations locations of each of the students
  *  @return numStudentsInfected int array of students infected by location
  */
  public static int[] updateInfections(int worldSize, int[] infections,
  int[] locations){
    //checks if infections is null because we use infections length
    //for initializing the return int array
    if(infections == null){
      return null;
    }

    //initializing an array of number of students infected
    int[] numStudentsInfected = new int[infections.length];

    //if worldsize is bigger than 0 and then arrays are not null
    if((worldSize > 0) && (locations != null) && (infections != null)
      &&(locations.length == infections.length)){
        //keeps track if arrays are valid
        int approval_count = 0;

        for(int i = 0; i < infections.length; i++){
          if((locations[i] < 0) || (locations[i] >= worldSize)
            || ((infections[i] != 0) && (infections[i] != 1))){
              return null;
          }
          else{
            approval_count++;
          }
        }

        //goes through each location and if location is exposed to COVID,
        //and a same location is not, we change it to be infected
        if(approval_count == infections.length){
          for(int i = 0; i < infections.length; i++){
            //infection count is used for keeping track of how many
            //students are infected from an individual person
            int infection_count = 0;
            if (infections[i] == 1){
              for (int j = 0; j < locations.length; j++){
                if (locations[j] == locations[i] && infections[j] == 0){
                  infection_count++;
                }

                else{
                  continue;
                }
              }
            }
            else{
              continue;
            }
            //the number of people that this person infected is charted here
            numStudentsInfected[i] = infection_count;
          }
          //this is where we change the infections of exposed places from
          //0 to 1
          for(int m = 0; m < infections.length; m++){
            int infection_count = 0;
            if (infections[m] == 1){
              for (int n = 0; n < locations.length; n++){
                if (locations[n] == locations[m] && infections[n] == 0){
                  infections[n] = 1;
                }
              }
            }
          }
        }
    }
    else{
      return null;
    }
    return numStudentsInfected;
  }

  /**
  *  This method updates the locations and infections of each
  *  student per day and outputs the record of which areas have
  *  been exposed by how many times.
  *
  *  @param days the amount of days that we want to document change
  *  @param worldSize the value of how big the world is that the
  *  students are in
  *  @param locations locations of each of the students
  *  @param movements where the students moved in relative to their location
  *  @param infections which respective element has an infection or not
  *  @return infectionRecord int array of students infected by location
  *  over the course of days
  */

  public static int[] countInfectionsByStudent(int days, int worldSize,
  int[] locations, int[] movements, int[] infections){
    //infectionRecord is kept for the record of infections per location
    //but infectionRecord_holder is kept to keep track of the new
    //updated Infection Record
    int [] infectionRecord = new int[infections.length];
    int [] infectionRecord_holder = new int[infections.length];
    int approval_count = 0;
    if((worldSize > 0) && (days > 0) && (locations != null)
      && (movements != null) && (infections != null)
      && (locations.length == movements.length)
      && (movements.length == infections.length)
      && (infections.length == locations.length)){
        for(int i = 0; i < locations.length; i++){
          if((locations[i] < 0) || (locations[i] >= worldSize)
            || ((infections[i] != 0) && (infections[i] != 1))){
              break;
          }
          else{
            approval_count++;
          }
        }
      }
      //we run through and update location and then update infection to
      //a new array. But since updateInfections makes a new array every time,
      //we use infectionRecord_holder to hold onto the most updated array
      //and add it to the infectionRecord
      if(approval_count == locations.length){
        for(int j = 0; j < days; j++){
          updateLocations(worldSize, locations, movements);
          infectionRecord_holder = updateInfections(worldSize, infections,
            locations);
          for(int k = 0; k < infections.length; k++){
            infectionRecord[k] += infectionRecord_holder[k];
          }
        }
      }
    return infectionRecord;
  }

  /**
  *  This method grabs the record of how many times locations had
  *  infected people and finds the average amount of people who will
  *  contract the disease
  *
  *  @param infectionRecord int array of students infected by location
  *  @return RNaught the average number of people who will contract disease
  */
  public static int findRNaught(int[] infectionRecord){
    int RNaught = 0;
    double RNaught_dbl = 0.0;
    int approval_count = 0;
    double sum = 0;
    int numStudents = 0;

    //this checks if all the arrays are valid
    if((infectionRecord != null) && (infectionRecord.length > 0)){
      for(int i = 0; i < infectionRecord.length; i++){
        if(infectionRecord[i] < 0){
          return -1;
        }
        else{
          approval_count++;
        }
      }
      //if the arrays are valid, find the average
      if(approval_count == infectionRecord.length){
        for(int j = 0; j < infectionRecord.length; j++){
          sum += infectionRecord[j];
        }
        numStudents = infectionRecord.length;
        //the average will come out as double, so use Math.ceil
        //to find the ceiling value of the double
        RNaught_dbl = Math.ceil(sum/numStudents);
        //then convert the double to an int for appropriate return value
        RNaught = (int)RNaught_dbl;
      }
    }
    else{
      return -1;
    }
    return RNaught;
  }

  /**
  *  This method runs through each college student and takes count of each
  *  person and how many times they exposed others to COVID. Then it points
  *  out who spread it the most first.
  *
  *  @param infectionRecord int array of students infected by location
  *  @param names names of the students involved
  *  @return superSpreader name of first individual that spread disease most
  */
  public static String findSuperSpreader(int[] infectionRecord,
    String[] names){
      //approval count keeps track if arrays are valid
      int approval_count = 0;
      //this is our max value of the superSpreader
      int superSpreader_max = 0;
      String superSpreader = null;

      //check if all arrays are valid
      if((infectionRecord != null) && (names != null)
        && (infectionRecord.length == names.length)
        && (infectionRecord.length > 0)){
          for(int i = 0; i < infectionRecord.length; i++){
            if(infectionRecord[i] < 0){
              return null;
            }
            else{
              approval_count++;
            }
          //if arrays are valid, then check if infectionRecord value
          //beats the previous max, then it updates the value of the
          //superSpreader_max and that person's name is updated
          //on superSpreader
          if(approval_count == infectionRecord.length){
            for(int j = 0; j < infectionRecord.length; j++){
              if(infectionRecord[j] > superSpreader_max){
                superSpreader_max = infectionRecord[j];
                superSpreader = names[j];
              }
            }
          }
        }
      }
      else{
        return null;
      }
      return superSpreader;
  }


}
