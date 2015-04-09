cls
echo "***************************"
echo "... Compilacion de la capa de negocio"

echo "************** EJB's !!!!!! *******"
cd..
:: ================================= Compilacion del acceso a datos ====================
echo "-> Compilacion del Modelo: rdu-model"
cd rdu-model
call mvn clean install -Dmaven.test.skip=true

:: ================================= Compilacion del acceso a datos  wedfwefrwefee ====================
echo "-> Acceso a datos"
cd..
cd rdu-ear
cd acceso-datos
call mvn clean install -Dmaven.test.skip=true
::================================= Compilacion del modulo de administracion de usuarios ====================
echo "-> Modulo de usuarios"
cd ..
cd ejb-rduUsuarios
call mvn clean install -Dmaven.test.skip=true
::================================= Compilacion del modulo de catalogos ====================
echo "-> Modulo de catalogos"
cd..
cd ejb-rduCatalogos
call mvn clean install -Dmaven.test.skip=true
::================================= Compilacion del modulo de flujos generales ====================
echo "-> Modulo de flujos generales"
cd..
cd ejb-rduFlujosGenerales
call mvn clean install -Dmaven.test.skip=true
::================================= Compilacion del modulo de Patentes ====================
echo "-> Modulo de Patentes"
cd..
cd ejb-rduPatentes
call mvn clean install -Dmaven.test.skip=true

::================================= Compilacion del EAR ====================
echo "************** EAR !!!!! *******"
cd..
call mvn clean install -Dmaven.test.skip=true

CHDIR
echo "... Termino la compilacion de del back"
echo "***************************"

