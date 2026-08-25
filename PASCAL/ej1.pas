program SumaNumerosNaturales;

var
  N, i, suma: integer;

begin
  writeln('Ingrese un numero N para sumar los primeros N numeros naturales: ');
  readln(N);
  
  suma := 0;
  
  for i := 1 to N do
  begin
    suma := suma + i;
  end;
  
  writeln('La suma de los primeros ', N, ' numeros naturales es: ', suma);
  
  readln; // Para que no se cierre la ventana
end.