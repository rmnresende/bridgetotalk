# =========================
# Build stage
# =========================
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copia apenas arquivos necessários para resolver dependências primeiro
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw -B -q dependency:go-offline

# Copia o restante do código
COPY src src

# Build do artefato
RUN ./mvnw -B -q clean package -DskipTests


# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copia apenas o jar final
COPY --from=builder /app/target/*.jar app.jar

# Porta padrão da aplicação
EXPOSE 8080

# Configuração explícita do profile
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]
