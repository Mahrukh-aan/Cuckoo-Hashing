import java.util.Arrays;

public class CuckooHashing {

        public static int[] hashtable1 = new int[10];
        public static int[] hashtable2 = new int[10];
        //public static int[] values_left=new int[5];
        public static int size1=0;
        public static int size2=0;

        public static void main(String[] args) {
            insert(20);
            insert(50);
            insert(53);
            insert(75);
            insert(100);
            insert(67);
            insert(105);
            insert(3);
            insert(36);
            insert(39);
            insert(6);
            insert(13);
            insert(14);
            insert(15);
            insert(7);
            insert(11);
            insert(29);
            insert(57);
            insert(95);
            insert(47);
            insert(33);
            insert(39);
            insert(51);
            //..........................................................

            System.out.println(search(20));
            System.out.println(search(0));

            display();
        }

        public static int hash1(int value){
            return value%hashtable1.length;

        }

        public static int hash2(int value){
            return (value/hashtable2.length)%hashtable2.length;
        }

        public static boolean search(int value){
            int index1=hash1(value);
            if(hashtable1[index1]==value){
                return true;
            }
            int index2=hash2(value);
            if(hashtable2[index2]==value){
                return true;
            }
            return false;
        }

        public static void display() {
            System.out.println("FIRST HASH TABLE");
            for (int i = 0; i < hashtable1.length; i++) {
                System.out.print(hashtable1[i] + " ");
            }

            System.out.println("\nSECOND HASH TABLE");
            for (int i = 0; i < hashtable2.length; i++) {
                System.out.print(hashtable2[i] + " ");
            }
        }

        public static void insert(int value){

            int displacements=0;
            int threshold= 5;
            double default_loadfactor=0.75;
            boolean isLeft=true;

            double lfactor1=0.0;
            double lfactor2=0.0;
            int occupying_value1=0;
            int occupying_value2=0;
            int new_index=0;

            while(displacements<=threshold){

                //hash using h1
                int index=hash1(value);

                //if no collision
                if(hashtable1[index]==0) {
                    hashtable1[index] = value;
                    size1++;
                    isLeft=false;
                    break;
                }
                //in case of collision
                else {

                    //get the value present
                    occupying_value1 = hashtable1[index];

                    //insert the new value in first hash table
                    hashtable1[index] = value;

                    //rehash using h2
                    new_index = hash2(occupying_value1);
                }

                //if no collision
                if(hashtable2[new_index]==0){
                    hashtable2[new_index]=occupying_value1;
                    size2++;
                    isLeft=false;
                    break;
                }

                //in case of collision
                else
                {
                    //get the value present
                    occupying_value2=hashtable2[new_index];

                    //insert the value of first hash table in second hash table
                    hashtable2[new_index]=occupying_value1;

                    //reset the values for next iteration
                    value=occupying_value2;
                }

                displacements++;

            }

            //CHECK IF REHASHING IS REQUIRED
            lfactor1=(1.0*size1)/hashtable1.length;
            lfactor2=(1.0*size2)/hashtable2.length;

            //if the current lfactor value exceeds 0.75 or a value entered a cycle of collisions among inserted keys and did not get inserted
            //In that case, we need rehashing and reinsert the value

            if(lfactor1>=default_loadfactor || isLeft==true){
                System.out.println("Rehashing required for hashtable1");
                rehash(hashtable1);
                insert(value);
                isLeft=false;

            }
            if(lfactor2>=default_loadfactor || isLeft==true){
                System.out.println("Rehashing required for hashtable2");
                rehash(hashtable2);

            }
        }

        public static void rehash(int[] hashtable){
            // define an array copyHashtable to copy contents of hashtable
            int[] copyHashtable = new int[hashtable.length];

            // copying via for loop
            for (int i=0; i<hashtable.length; i++){
                copyHashtable[i] = hashtable[i];
            }

            int[] new_hashtable = new int[2*hashtable.length];

            if(Arrays.equals(hashtable,hashtable1)){
                hashtable1=new_hashtable;
                size1=0;
            }
            else
            {
                hashtable2=new_hashtable;
                size1=0;
            }

            for(int j=0; j<copyHashtable.length; j++){
                if(copyHashtable[j]!=0){
                    insert(copyHashtable[j]);
                }
            }
        }



}
