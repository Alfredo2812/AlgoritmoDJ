
import java.io.*;
import java.util.*;

class dijkstra{

  int[][] matrizAdy;
  int nNodos;
  List conj_S = new ArrayList();
  List conjComp_S = new ArrayList();
  List caminos = new ArrayList();
  String tmp;
  InputStreamReader l1;
  BufferedReader l2;

  dijkstra(int numNodos){
    matrizAdy = new int[numNodos][numNodos];
    int aux=0;
    l1 = new InputStreamReader(System.in);
    l2 = new BufferedReader(l1);
    nNodos=numNodos;

      ingresanodos();
    
    do{
      try{
        System.out.print(" * Cual es el nodo origen: ");
        aux=((int)((l2.readLine()).toUpperCase()).charAt(0))-65;
      }
      catch(IOException e2){
        System.out.println("Error: "+e2);
        aux=-1;
      }
      catch(StringIndexOutOfBoundsException e3){
        System.out.println("Error: "+e3);
        aux=-1;
      }
    }while(aux<0 || aux>nNodos-1);
    matrizAdy[aux][aux]=0;
    resuelve(aux);
  }

  private void ingresanodos(){
    boolean ocurrioError;
    System.out.println(" * Ahora ingresa los datos que se te soliciten: ");
    for(int cuenta=1;cuenta<=nNodos;cuenta++)
      for(int cnt=1;cnt<=nNodos;cnt++){
        if(cnt!=cuenta){
          System.out.println("Costo de la arista dirigida del nodo "+(char)(cuenta+64)+" al nodo "+(char)(cnt+64));
          System.out.print("(Ingresa 0 si la arista no existe) ");
          ocurrioError=false;
          try{
            matrizAdy[cuenta-1][cnt-1]=Integer.valueOf(l2.readLine()).intValue();
            ocurrioError=(matrizAdy[cuenta-1][cnt-1]<0?true:false);
            matrizAdy[cuenta-1][cnt-1]=(matrizAdy[cuenta-1][cnt-1]==0?-1:matrizAdy[cuenta-1][cnt-1]);
          }
          catch(IOException e0){
            System.out.println("Error: "+e0);
            ocurrioError=true;
          }
          catch(NumberFormatException e){
            System.out.println("Error: "+e);
            ocurrioError=true;
          }
          if(ocurrioError)
            cnt--;
        }
        else
          matrizAdy[cuenta-1][cuenta-1]=-1;
      }
  }

  private void resuelve(int origen){
    int nod;
    int minimo;
    int aux;
    int nodCambio=0;
    int intento;
    String tmp2;
    //Inicializando listas
    for(int i=0;i<nNodos;i++){
      if(i!=origen)
        conjComp_S.add(""+i);
      else
        conj_S.add(""+i);
      caminos.add("");
    }
    //Aplicando ciclo i de diksjtra
    for(int i=0;i<nNodos;i++){
      minimo=-1;
      for(int j=0;j<conjComp_S.size();j++){
        nod=Integer.valueOf((String)(conjComp_S.get(j))).intValue();
        aux=min(nod);
        if(minimo==-1 || (aux<minimo && aux!=-1)){
          minimo=aux;
          nodCambio=j;
        }
      }
      if(minimo!=-1){
        conj_S.add(""+(String)(conjComp_S.get(nodCambio)));
        conjComp_S.remove(nodCambio);
      }
    }
    //Imprimiendo resultados
    System.out.print("\n -> Resultados <-");
    for(int k=0;k<caminos.size();k++)
      if(k!=origen){
        tmp=(String)(caminos.get(k))+(char)(k+65);
        caminos.set(k,tmp);
      }
    for(int j=0;j<caminos.size();j++)
      if(j!=origen){
        intento=0;
        tmp=(String)(caminos.get(j));
          while(tmp.charAt(0)!=(char)(origen+65) && intento<10){
            aux=tmp.charAt(0)-65;
            tmp=((String)(caminos.get(aux)))+tmp.substring(1,tmp.length());
            if(++intento==10)
              tmp="*"+tmp;
          };
        imprimeCamino(tmp,j,origen);
      }
    System.out.println("\n <-  Que tenga un buen viaje! ->\n");
  }

  private int min(int dest){
    int min=-1;
    int nod=0;
    int nodOrig=-1;
    int aux;
    for(int i=0;i<conj_S.size();i++){
      nod=Integer.valueOf((String)(conj_S.get(i))).intValue();
      if(matrizAdy[nod][nod]!=-1 && matrizAdy[nod][dest]!=-1)
        aux=matrizAdy[nod][nod]+matrizAdy[nod][dest];
      else
        aux=-1;
      if((aux<min && aux!=-1)||min==-1){
        min=aux;
        nodOrig=nod;
      }
    }
    if(min!=-1){
      matrizAdy[dest][dest]=min;
      caminos.set(dest,""+(char)(nodOrig+65));
    }
    return min;
  }

  private void imprimeCamino(String cam, int nod, int o){
    System.out.print("\nCamino: ");
    if(cam.charAt(0)=='*')
      System.out.print("Te jodes: no hay camino de: "+(char)(o+65)+" a: "+cam.charAt(cam.length()-1)+"!!");
    else{
      for(int i=0;i<cam.length();i++)
        System.out.print(""+cam.charAt(i)+(i==cam.length()-1?"":"->"));
      System.out.print(" costo: "+matrizAdy[nod][nod]);
    }
  }
  
  public static void main(String args[]){

    int num=0;

    System.out.println("\n\tImplementacion del algoritmo de Dijkstra");
    System.out.print("Numero de nodos que tiene el grafo a resolver? ");

    do{
      try{
        InputStreamReader l1 = new InputStreamReader(System.in);
        BufferedReader l2 = new BufferedReader(l1);
        num=Integer.valueOf(l2.readLine()).intValue();
      }
      catch(IOException e){
        System.out.println("Error: "+e);
        System.out.println("Ingresa el numero de nodos que tiene el grafo a resolver: ");
      }
      catch(NumberFormatException e2){
        System.out.println("Error: "+e2);
        System.out.println("Ingresa el numero de nodos que tiene el grafo a resolver: ");
      }
      if(num<3 || num>26)
       System.out.print(" * El numero de nodos debe estar entre 3 y 26 ");

    }while(num<3 || num>26);
    dijkstra obj = new dijkstra(num);
  }
}
