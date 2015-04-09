clear
echo "***************************"
echo "... Compilacion de la capa de negocio"

echo "************** EJB's !!!!!! *******"
cd ..
export REPO_MP_PATH=`pwd`

#================================= Compilacion del acceso a datos ====================
#echo "-> Compilacion del Modelo: rdu-model"
cd $REPO_MP_PATH
cd rdu-model
mvn clean install -Dmaven.test.skip=true 

#================================= Compilacion del acceso a datos ====================
echo "-> Acceso a datos"
cd $REPO_MP_PATH
cd rdu-ear
cd acceso-datos
mvn clean install -Dmaven.test.skip=true 



#================================= Compilacion del modulo de administracion de usuarios ====================
echo "-> Modulo de usuarios"
cd $REPO_MP_PATH
cd rdu-ear
cd ejb-rduUsuarios
mvn clean install -Dmaven.test.skip=true 
#================================= Compilacion del modulo de catalogos ====================
echo "-> Modulo de catalogos"
cd $REPO_MP_PATH
cd rdu-ear
cd ejb-rduCatalogos
mvn clean install -Dmaven.test.skip=true 
#================================= Compilacion del modulo de flujos generales ====================
echo "-> Modulo de flujos generales"
cd $REPO_MP_PATH
cd rdu-ear
cd ejb-rduFlujosGenerales
mvn clean install -Dmaven.test.skip=true 
#================================= Compilacion del modulo de Patentes ====================
echo "-> Modulo de Patentes"
cd $REPO_MP_PATH
cd rdu-ear
cd ejb-rduPatentes
mvn clean install -Dmaven.test.skip=true 

#================================= Compilacion del EAR ====================
echo "************** EAR !!!!! *******"
cd $REPO_MP_PATH
cd rdu-ear
mvn clean install -Dmaven.test.skip=true 

cd $REPO_MP_PATH
echo "... Termino la compilacion de del back"
echo "***************************"


#================================= Deploy del EAR ====================
echo "************** EAR !!!!! *******"
#/Applications/NetBeans/glassfish3/glassfish/bin/asadmin -u admin --passwordfile $REPO_MP_PATH/rdu-ppi/admin_pass.txt deploy --force=true $REPO_MP_PATH/rdu-ear/target/rdu-ear-1.0.1.ear

echo "... Termino el deploy"
echo "***************************"


