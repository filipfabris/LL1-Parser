# LL1-Parser

## Tests

Start script `check.sh` using command `bash check.sh`

* NOTE: you had to update classpath inside check.sh to correspond to your *.class folder

## `Token Types:`
 * IDN, // identifikator
	
* BROJ, // konstante
	
* OP_PRIDRUZI, // =
	
* OP_PLUS, // +
	
* OP_MINUS, // -
	
* OP_PUTA, // *
	
* OP_DIJELI, // /
	
* L_ZAGRADA, // (
	
* D_ZAGRADA, // )
	
* KR_ZA, // start of while
	
* KR_OD, // of index
	
* KR_DO, // to index
	
* KR_AZ, // end of while
	
* COMMENT,
	
* EOF, //end of program, fila

## Example
Program:
```
d = 0
za a od 1 do 5
  za b od -a do +a
    za c od b+a do b*(d+3)
      d = d + a*b*c
    az
  az
  za c od b-a do b*(d-3)
    d = d + 1
  az
  za c od 0 do 100
  az
az

```

Input (Tokens):
```
IDN 1 d
OP_PRIDRUZI 1 =
BROJ 1 0
KR_ZA 2 za
IDN 2 a
KR_OD 2 od
BROJ 2 1
KR_DO 2 do
BROJ 2 5
KR_ZA 3 za
IDN 3 b
KR_OD 3 od
OP_MINUS 3 -
IDN 3 a
KR_DO 3 do
OP_PLUS 3 +
IDN 3 a
KR_ZA 4 za
IDN 4 c
KR_OD 4 od
IDN 4 b
OP_PLUS 4 +
IDN 4 a
KR_DO 4 do
IDN 4 b
OP_PUTA 4 *
L_ZAGRADA 4 (
IDN 4 d
OP_PLUS 4 +
BROJ 4 3
D_ZAGRADA 4 )
IDN 5 d
OP_PRIDRUZI 5 =
IDN 5 d
OP_PLUS 5 +
IDN 5 a
OP_PUTA 5 *
IDN 5 b
OP_PUTA 5 *
IDN 5 c
KR_AZ 6 az
KR_AZ 7 az
KR_ZA 8 za
IDN 8 c
KR_OD 8 od
IDN 8 b
OP_MINUS 8 -
IDN 8 a
KR_DO 8 do
IDN 8 b
OP_PUTA 8 *
L_ZAGRADA 8 (
IDN 8 d
OP_MINUS 8 -
BROJ 8 3
D_ZAGRADA 8 )
IDN 9 d
OP_PRIDRUZI 9 =
IDN 9 d
OP_PLUS 9 +
BROJ 9 1
KR_AZ 10 az
KR_ZA 11 za
IDN 11 c
KR_OD 11 od
BROJ 11 0
KR_DO 11 do
BROJ 11 100
KR_AZ 12 az
KR_AZ 13 az
```

Output:
```
<program>
 <lista_naredbi>
  <naredba>
   <naredba_pridruzivanja>
    IDN 1 d
    OP_PRIDRUZI 1 =
    <E>
     <T>
      <P>
       BROJ 1 0
      <T_lista>
       $
     <E_lista>
      $
  <lista_naredbi>
   <naredba>
    <za_petlja>
     KR_ZA 2 za
     IDN 2 a
     KR_OD 2 od
     <E>
      <T>
       <P>
        BROJ 2 1
       <T_lista>
        $
      <E_lista>
       $
     KR_DO 2 do
     <E>
      <T>
       <P>
        BROJ 2 5
       <T_lista>
        $
      <E_lista>
       $
     <lista_naredbi>
      <naredba>
       <za_petlja>
        KR_ZA 3 za
        IDN 3 b
        KR_OD 3 od
        <E>
         <T>
          <P>
           OP_MINUS 3 -
           <P>
            IDN 3 a
          <T_lista>
           $
         <E_lista>
          $
        KR_DO 3 do
        <E>
         <T>
          <P>
           OP_PLUS 3 +
           <P>
            IDN 3 a
          <T_lista>
           $
         <E_lista>
          $
        <lista_naredbi>
         <naredba>
          <za_petlja>
           KR_ZA 4 za
           IDN 4 c
           KR_OD 4 od
           <E>
            <T>
             <P>
              IDN 4 b
             <T_lista>
              $
            <E_lista>
             OP_PLUS 4 +
             <E>
              <T>
               <P>
                IDN 4 a
               <T_lista>
                $
              <E_lista>
               $
           KR_DO 4 do
           <E>
            <T>
             <P>
              IDN 4 b
             <T_lista>
              OP_PUTA 4 *
              <T>
               <P>
                L_ZAGRADA 4 (
                <E>
                 <T>
                  <P>
                   IDN 4 d
                  <T_lista>
                   $
                 <E_lista>
                  OP_PLUS 4 +
                  <E>
                   <T>
                    <P>
                     BROJ 4 3
                    <T_lista>
                     $
                   <E_lista>
                    $
                D_ZAGRADA 4 )
               <T_lista>
                $
            <E_lista>
             $
           <lista_naredbi>
            <naredba>
             <naredba_pridruzivanja>
              IDN 5 d
              OP_PRIDRUZI 5 =
              <E>
               <T>
                <P>
                 IDN 5 d
                <T_lista>
                 $
               <E_lista>
                OP_PLUS 5 +
                <E>
                 <T>
                  <P>
                   IDN 5 a
                  <T_lista>
                   OP_PUTA 5 *
                   <T>
                    <P>
                     IDN 5 b
                    <T_lista>
                     OP_PUTA 5 *
                     <T>
                      <P>
                       IDN 5 c
                      <T_lista>
                       $
                 <E_lista>
                  $
            <lista_naredbi>
             $
           KR_AZ 6 az
         <lista_naredbi>
          $
        KR_AZ 7 az
      <lista_naredbi>
       <naredba>
        <za_petlja>
         KR_ZA 8 za
         IDN 8 c
         KR_OD 8 od
         <E>
          <T>
           <P>
            IDN 8 b
           <T_lista>
            $
          <E_lista>
           OP_MINUS 8 -
           <E>
            <T>
             <P>
              IDN 8 a
             <T_lista>
              $
            <E_lista>
             $
         KR_DO 8 do
         <E>
          <T>
           <P>
            IDN 8 b
           <T_lista>
            OP_PUTA 8 *
            <T>
             <P>
              L_ZAGRADA 8 (
              <E>
               <T>
                <P>
                 IDN 8 d
                <T_lista>
                 $
               <E_lista>
                OP_MINUS 8 -
                <E>
                 <T>
                  <P>
                   BROJ 8 3
                  <T_lista>
                   $
                 <E_lista>
                  $
              D_ZAGRADA 8 )
             <T_lista>
              $
          <E_lista>
           $
         <lista_naredbi>
          <naredba>
           <naredba_pridruzivanja>
            IDN 9 d
            OP_PRIDRUZI 9 =
            <E>
             <T>
              <P>
               IDN 9 d
              <T_lista>
               $
             <E_lista>
              OP_PLUS 9 +
              <E>
               <T>
                <P>
                 BROJ 9 1
                <T_lista>
                 $
               <E_lista>
                $
          <lista_naredbi>
           $
         KR_AZ 10 az
       <lista_naredbi>
        <naredba>
         <za_petlja>
          KR_ZA 11 za
          IDN 11 c
          KR_OD 11 od
          <E>
           <T>
            <P>
             BROJ 11 0
            <T_lista>
             $
           <E_lista>
            $
          KR_DO 11 do
          <E>
           <T>
            <P>
             BROJ 11 100
            <T_lista>
             $
           <E_lista>
            $
          <lista_naredbi>
           $
          KR_AZ 12 az
        <lista_naredbi>
         $
     KR_AZ 13 az
   <lista_naredbi>
    $
```
