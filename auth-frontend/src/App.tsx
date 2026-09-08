import {
  ArrowRight,
  Check,
  Lock,
  ShieldCheck,
  Sparkles,
  Zap,
} from "lucide-react";
import { Button } from "./components/ui/button";
import { NavLink } from "react-router";
import { motion } from "motion/react";

function App() {
  return (
    <main className="min-h-screen overflow-hidden bg-background text-foreground transition-colors">
      {/* Hero */}
      <section className="relative flex min-h-[calc(100vh-4rem)] items-center justify-center overflow-hidden px-6">
        {/* Background glow */}
        <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(circle_at_50%_30%,rgba(99,102,241,0.12),transparent_45%)] dark:bg-[radial-gradient(circle_at_50%_30%,rgba(99,102,241,0.18),transparent_45%)]" />

        <div className="relative z-10 mx-auto max-w-4xl text-center">
          {/* Badge */}
          <div className="mb-8 inline-flex items-center gap-2 rounded-full border border-border bg-muted/50 px-5 py-2 text-sm text-muted-foreground backdrop-blur-sm">
            <Sparkles className="h-4 w-4 text-purple-500" />
            Modern authentication, built for developers
          </div>

          {/* Heading */}
          <h1 className="text-5xl font-bold tracking-tight sm:text-6xl lg:text-7xl">
            <motion.h1
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-5xl font-bold tracking-tight sm:text-6xl lg:text-7xl"
          >
            <span className="block text-foreground">
              Authentication
            </span>
            </motion.h1>
            <span className="mt-2 block bg-gradient-to-r from-indigo-500 via-purple-500 to-cyan-500 bg-clip-text text-transparent">
              without the complexity.
            </span>
          </h1>

          {/* Description */}
          <p className="mx-auto mt-8 max-w-3xl text-lg leading-8 text-muted-foreground sm:text-xl">
            Secure authentication infrastructure for modern applications.
            Handle users, sessions and security while you focus on building
            what matters.
          </p>

          {/* Buttons */}
          <div className="mt-10 flex justify-center gap-4">
            <NavLink to="/register">
              <Button size="lg">
                Get started
                <ArrowRight className="ml-2 h-4 w-4" />
              </Button>
            </NavLink>

            <NavLink to="/login">
              <Button size="lg" variant="outline">
                Sign in
              </Button>
            </NavLink>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="border-t border-border py-24">
        <div className="mx-auto max-w-7xl px-6">
          <div className="mx-auto max-w-2xl text-center">
            <p className="text-sm font-medium uppercase tracking-widest text-indigo-500">
              Features
            </p>

            <h2 className="mt-4 text-3xl font-bold tracking-tight text-foreground md:text-5xl">
              Everything you need for authentication
            </h2>

            <p className="mt-5 text-muted-foreground">
              A complete authentication foundation designed to be secure,
              simple and developer-friendly.
            </p>
          </div>

          <div className="mt-16 grid gap-6 md:grid-cols-2 lg:grid-cols-3">
            <FeatureCard
              icon={<Lock />}
              title="Secure authentication"
              description="Protect your users with secure password hashing, authentication flows and token-based sessions."
            />

            <FeatureCard
              icon={<ShieldCheck />}
              title="JWT security"
              description="Use access and refresh tokens to create secure and scalable authentication sessions."
            />

            <FeatureCard
              icon={<Zap />}
              title="Fast API"
              description="Built with a clean REST architecture that makes authentication easy to integrate into your applications."
            />

            <FeatureCard
              icon={<Sparkles />}
              title="OAuth ready"
              description="Extend authentication with OAuth providers and modern identity flows."
            />

            <FeatureCard
              icon={<Check />}
              title="Session management"
              description="Keep authentication state secure with controlled session and token lifecycle management."
            />

            <FeatureCard
              icon={<ArrowRight />}
              title="Developer focused"
              description="Simple APIs and predictable authentication flows designed to get you productive quickly."
            />
          </div>
        </div>
      </section>

      {/* How it works */}
      <section className="py-24">
        <div className="mx-auto max-w-7xl px-6">
          <div className="grid gap-16 md:grid-cols-2 md:items-center">
            <div>
              <p className="text-sm font-medium uppercase tracking-widest text-indigo-500">
                How it works
              </p>

              <h2 className="mt-4 text-3xl font-bold text-foreground md:text-5xl">
                Authentication made simple.
              </h2>

              <p className="mt-5 leading-7 text-muted-foreground">
                Auth01 handles the authentication layer so your application
                can focus on delivering its core experience.
              </p>
            </div>

            <div className="space-y-4">
              <Step
                number="01"
                title="Create an account"
                description="Register securely and create your authentication identity."
              />

              <Step
                number="02"
                title="Authenticate"
                description="Sign in and receive a secure access token."
              />

              <Step
                number="03"
                title="Build securely"
                description="Use authenticated requests to access protected resources."
              />
            </div>
          </div>
        </div>
      </section>

      {/* Security */}
      <section className="py-24">
        <div className="mx-auto max-w-5xl px-6">
          <div className="relative overflow-hidden rounded-3xl border border-indigo-500/20 bg-indigo-500/[0.05] p-10 text-center md:p-16">
            <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(circle_at_center,rgba(99,102,241,0.12),transparent_60%)] dark:bg-[radial-gradient(circle_at_center,rgba(99,102,241,0.18),transparent_60%)]" />

            <div className="relative">
              <ShieldCheck className="mx-auto h-12 w-12 text-indigo-500" />

              <h2 className="mt-6 text-3xl font-bold text-foreground md:text-4xl">
                Security shouldn't be an afterthought.
              </h2>

              <p className="mx-auto mt-5 max-w-2xl text-muted-foreground">
                Build your application on top of an authentication layer
                designed around secure identity, protected sessions and
                modern authentication practices.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Final CTA */}
      <section className="px-6 py-24">
        <div className="mx-auto flex max-w-4xl flex-col items-center text-center">
          <h2 className="max-w-3xl text-4xl font-bold tracking-tight text-foreground md:text-6xl">
            Ready to secure your app?
          </h2>

          <p className="mt-5 max-w-xl leading-7 text-muted-foreground">
            Start building with Auth01 and give your application a secure
            authentication foundation.
          </p>

          <NavLink to="/register">
            <Button
              size="lg"
              className="mt-8 cursor-pointer bg-foreground text-background hover:bg-foreground/90"
            >
              Create your account
              <ArrowRight className="ml-2 h-4 w-4" />
            </Button>
          </NavLink>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-border py-8">
        <div className="mx-auto flex max-w-7xl flex-col items-center justify-between gap-4 px-6 text-sm text-muted-foreground md:flex-row">
          <div className="flex items-center gap-2">
            <span className="flex h-7 w-7 items-center justify-center rounded-md bg-gradient-to-r from-indigo-500 to-purple-500 text-xs font-bold text-white">
              A
            </span>

            <span className="font-medium text-foreground">
              Auth01
            </span>
          </div>

          <p>Secure authentication for modern applications.</p>

          <p>© 2026 Auth01</p>
        </div>
      </footer>
    </main>
  );
}

/* Feature Card */

function FeatureCard({
  icon,
  title,
  description,
}: {
  icon: React.ReactNode;
  title: string;
  description: string;
}) {
  return (
    <div className="group rounded-2xl border border-border bg-card p-6 transition-all duration-300 hover:-translate-y-1 hover:border-indigo-500/30 hover:shadow-lg hover:shadow-indigo-500/5">
      <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-indigo-500/10 text-indigo-500 transition-colors group-hover:bg-indigo-500/15">
        {icon}
      </div>

      <h3 className="mt-5 text-lg font-semibold text-card-foreground">
        {title}
      </h3>

      <p className="mt-2 text-sm leading-6 text-muted-foreground">
        {description}
      </p>
    </div>
  );
}

/* Steps */

function Step({
  number,
  title,
  description,
}: {
  number: string;
  title: string;
  description: string;
}) {
  return (
    <div className="flex gap-5 rounded-2xl border border-border bg-card p-5 transition-colors hover:bg-muted/50">
      <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full border border-indigo-500/30 bg-indigo-500/10 text-sm font-semibold text-indigo-500">
        {number}
      </div>

      <div>
        <h3 className="font-semibold text-card-foreground">
          {title}
        </h3>

        <p className="mt-1 text-sm leading-6 text-muted-foreground">
          {description}
        </p>
      </div>
    </div>
  );
}

export default App;
