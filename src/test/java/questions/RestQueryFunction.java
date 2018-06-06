package questions;

import io.restassured.specification.RequestSpecification;

import java.util.function.Function;

interface RestQueryFunction extends Function<RequestSpecification,RequestSpecification> {}