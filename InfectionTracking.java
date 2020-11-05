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
          if((locations[j] + movements[j]) < 0){
            locations[j] = ((locations[j] + movements[j]) % worldSize)
            + worldSize;
          }
          else{
            locations[j] = (locations[j] + movements[j]) % worldSize;
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



  public static int[] countInfectionsByStudent(int days, int worldSize,
  int[] locations, int[] movements, int[] infections){
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
      if(approval_count == locations.length){
        for(int j = 0; j < days; j++){
          updateLocations(worldSize, locations, movements);
          infectionRecord_holder = updateInfections(worldSize, infections, locations);
          for(int k = 0; k < infections.length; k++){
            infectionRecord[k] += infectionRecord_holder[k];
          }
        }
      }
    return infectionRecord;
  }

  public static int findRNaught(int[] infectionRecord){
    int RNaught = 0;
    double RNaught_dbl = 0.0;
    int approval_count = 0;
    double sum = 0;
    int numStudents = 0;

    if((infectionRecord != null) && (infectionRecord.length > 0)){
      for(int i = 0; i < infectionRecord.length; i++){
        if(infectionRecord[i] < 0){
          return -1;
        }
        else{
          approval_count++;
        }
      }
      if(approval_count == infectionRecord.length){
        for(int j = 0; j < infectionRecord.length; j++){
          sum += infectionRecord[j];
        }
        numStudents = infectionRecord.length;
        RNaught_dbl = Math.ceil(sum/numStudents);
        RNaught = (int)RNaught_dbl;
      }
    }
    else{
      return -1;
    }
    return RNaught;
  }


  public static String findSuperSpreader(int[] infectionRecord,
    String[] names){
      int approval_count = 0;
      int superSpreader_max = 0;
      String superSpreader = null;

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


  public static void main(String[] args) throws IOException{
    /**
    String[] names = new String[4];
    int[] locations = new int[4];
    int[] movements = new int[4];
    int[] infections = new int[4];
    System.out.println(populateArrays("Students.csv", names, locations,
    movements, infections));
    */

    //**
    int worldSize = 15;
    int locations[] = {3, 2, 1, 0, 10, 12, 14, 9, 5};
    int movements[] = {-3, -9, 5, 7, -14, 9, 10, -2, 14};
    updateLocations(worldSize, locations, movements);
    //*/

    /**
    int worldSize = 6;
    int infections[] = {0, 0, 1, 1, 0, 0, 1, 0, 1};
    int locations[] = {2, 1, 4, 3, 3, 5, 1, 1, 3};
    System.out.println(Arrays.toString(updateInfections(worldSize,
    infections, locations)));
    */

    /**
    int days = 3;
    int worldSize = 10;
    int locations[] = {3, 2, 1, 0, 5, 2, 9};
    int movements[] = {2, -3, 1, -2, 3, 2, 5};
    int infections[] = {0, 1, 0, 0, 0, 0, 1};
    System.out.println(Arrays.toString(countInfectionsByStudent(days,
    worldSize, locations, movements, infections)));
    */

    /**
    int infectionRecord[] = {2, 1, 0, 4, 7, 3, 1};
    System.out.println(findRNaught(infectionRecord));
    */

    /**
    int infectionRecord[] = {3, 4, 1, 0, 2, 4};
    String names[] = {"Isaac", "Kevin", "Mary", "Sally", "Miranda", "Bob"};
    System.out.println(findSuperSpreader(infectionRecord, names));
    */
  }


}
