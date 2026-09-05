package io.github.yuzhiyang1.agentstarter.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * 防止 COLA 模块依赖在后续迁移中倒置或形成循环。
 */
@AnalyzeClasses(packages = "io.github.yuzhiyang1.agentstarter")
class LayerDependencyTest {

    @ArchTest
    static final ArchRule CLIENT_IS_INDEPENDENT = noClasses()
            .that().resideInAPackage("..client..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..domain..", "..app..", "..infrastructure..", "..adapter..", "..start.."
            );

    @ArchTest
    static final ArchRule DOMAIN_IS_INDEPENDENT = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..client..", "..app..", "..infrastructure..", "..adapter..", "..start.."
            );

    @ArchTest
    static final ArchRule APP_ONLY_DEPENDS_INWARD = noClasses()
            .that().resideInAPackage("..app..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..infrastructure..", "..adapter..", "..start.."
            );

    @ArchTest
    static final ArchRule INFRASTRUCTURE_DOES_NOT_DEPEND_ON_DRIVING_ADAPTERS = noClasses()
            .that().resideInAPackage("..infrastructure..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..client..", "..app..", "..adapter..", "..start.."
            );

    @ArchTest
    static final ArchRule ADAPTER_ONLY_CALLS_CLIENT_CONTRACT = noClasses()
            .that().resideInAPackage("..adapter..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..domain..", "..app..", "..infrastructure..", "..start.."
            );

    @ArchTest
    static final ArchRule MODULES_ARE_FREE_OF_CYCLES = slices()
            .matching("io.github.yuzhiyang1.agentstarter.(*)..")
            .should().beFreeOfCycles();
}
