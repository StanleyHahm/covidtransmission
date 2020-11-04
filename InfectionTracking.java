import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.Arrays; //this helps me print out arrays

public class InfectionTracking{
  public static int populateArrays(String PathToFile, String[] names,
  int[] locations, int[] movements, int[] infections) throws IOException {
    File sourceFile = new File(PathToFile);
    Scanner input = new Scanner(sourceFile);
    input.useDelimiter("\\s*[,\\n]\\s*");
    int maxLocation = 0; //the max location area

    if(PathToFile != null){
      int count = 0;
      while(input.hasNext() && count < names.length){
        names[count] = input.next();
        locations[count] = input.nextInt();
        movements[count] = input.nextInt();
        infections[count] = input.nextInt();
        count++;
      }
      if((names.length == locations.length)
        && (names.length == movements.length)
        && (names.length == infections.length)){

        for(int i = 0; i < locations.length; i++){
          if (locations[i] > maxLocation - 1){
            maxLocation = locations[i] + 1;
          }
        }
      }
      else{
        return -1;
      }
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

  public static void updateLocations(int worldSize, int[] locations,
  int[] movements) {
    if((locations != null) && (movements != null) && (worldSize > 0)
    && (locations.length == movements.length)){
      int approval_count = 0;
      for(int i = 0; i < locations.length; i++){
        if((locations[i] < 0) || (locations[i] >= worldSize)
          || movements[i] == 0){
            break;
        }
        else{
          approval_count++;
        }
      }
      if(approval_count == locations.length){
        for(int j = 0; j < locations.length; j++){
          locations[j] = (locations[j] + movements[j]) % worldSize;
          if(locations[j] < 0){
            locations[j] += worldSize;
          }
        }
      }
    }
    System.out.println(Arrays.toString(locations));
  }

  public static int[] updateInfections(int worldSize, int[] infections,
  int[] locations){
    int[] numStudentsInfected = new int[infections.length];

    if((worldSize > 0) && (locations != null) && (infections != null)
      &&(locations.length == infections.length)){
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

      if(approval_count == infections.length){
        for(int i = 0; i < infections.length; i++){
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
          numStudentsInfected[i] = infection_count;
        }
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
  public static int[] countInfectionsByStudent(int days, int worldSize,
  int[] locations, int[] movements, int[] infections){

  }
*/


  public static void main(String[] args) throws IOException{
    /**
    String[] names = new String[4];
    int[] locations = new int[4];
    int[] movements = new int[4];
    int[] infections = new int[4];
    System.out.println(populateArrays("Students.csv", names, locations,
    movements, infections));
    */

    /**
    int worldSize = 15;
    int locations[] = {3, 2, 1, 0, 10, 12, 14, 9, 5};
    int movements[] = {1, -1, 5, 7, -14, 9, 10, -2, 14};
    updateLocations(worldSize, locations, movements);
    //*/

    //**
    int worldSize = 6;
    int infections[] = {0, 0, 1, 1, 0, 0, 1, 0, 1};
    int locations[] = {2, 1, 4, 3, 3, 5, 1, 1, 3};
    System.out.println(Arrays.toString(updateInfections(worldSize,
    infections, locations)));
    //*/
  }


}
